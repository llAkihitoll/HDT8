import java.util.Scanner;

public class MainVectorHeap {
    public static void main(String[] args) {
        VectorHeap<Paciente> cola = new VectorHeap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese datos del paciente (nombre, síntoma, prioridad [A-E]). Escriba 'fin' para terminar.");

        while (true) {
            System.out.print("Paciente: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("fin")) break;

            String[] partes = input.split(",");
            if (partes.length == 3) {
                try {
                    String nombre = partes[0].trim();
                    String sintoma = partes[1].trim();
                    char prioridad = partes[2].trim().toUpperCase().charAt(0);
                    if ("ABCDE".indexOf(prioridad) >= 0) {
                        cola.add(new Paciente(nombre, sintoma, prioridad));
                    } else {
                        System.out.println("⚠️ Prioridad inválida. Use A, B, C, D o E.");
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Entrada inválida. Intente nuevamente.");
                }
            } else {
                System.out.println("⚠️ Formato incorrecto. Use: nombre, síntoma, prioridad");
            }
        }

        System.out.println("\nAtendiendo pacientes (VectorHeap):");
        while (!cola.isEmpty()) {
            System.out.println(cola.remove());
        }
    }
}
