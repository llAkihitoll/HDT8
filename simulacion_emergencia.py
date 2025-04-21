import simpy
import random

# Mapeo de prioridad de A-E a valores enteros
PRIORIDAD_MAPA = {'A': 0, 'B': 1, 'C': 2, 'D': 3, 'E': 4}

class Paciente:
    def __init__(self, nombre, sintoma, prioridad):
        self.nombre = nombre
        self.sintoma = sintoma
        self.prioridad = PRIORIDAD_MAPA[prioridad]

    def __lt__(self, otro):
        return self.prioridad < otro.prioridad

def atender_paciente(env, paciente, medicos):
    llegada = env.now
    print(f"{env.now:.2f} - {paciente.nombre} llega con prioridad {paciente.prioridad}")

    with medicos.request(priority=paciente.prioridad) as req:
        yield req
        espera = env.now - llegada
        print(f"{env.now:.2f} - {paciente.nombre} es atendido tras esperar {espera:.2f} minutos.")
        duracion = random.randint(5, 15)
        yield env.timeout(duracion)
        print(f"{env.now:.2f} - {paciente.nombre} termina atención ({duracion} min).")

def generador_pacientes(env, lista_pacientes, medicos):
    for paciente in lista_pacientes:
        yield env.timeout(random.randint(1, 4))
        env.process(atender_paciente(env, paciente, medicos))

def simular_emergencias(lista_pacientes, num_medicos=2):
    print("▶️ Iniciando simulación...
")
    env = simpy.Environment()
    medicos = simpy.PriorityResource(env, capacity=num_medicos)
    env.process(generador_pacientes(env, lista_pacientes, medicos))
    env.run()
    print("\n✅ Simulación finalizada.")

if __name__ == "__main__":
    lista = []
    print("Ingrese los datos de los pacientes (nombre, sintoma, prioridad - A a E). Escriba 'fin' para terminar.")
    while True:
        entrada = input("Paciente (ej. Juan Perez, fractura, B): ")
        if entrada.strip().lower() == 'fin':
            break
        try:
            nombre, sintoma, prioridad = map(str.strip, entrada.split(','))
            if prioridad not in PRIORIDAD_MAPA:
                raise ValueError("Prioridad no válida.")
            lista.append(Paciente(nombre, sintoma, prioridad))
        except Exception as e:
            print(f"Entrada inválida: {e}. Intente de nuevo.")

    simular_emergencias(lista)
