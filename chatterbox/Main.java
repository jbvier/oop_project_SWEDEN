
package chatterbox;

import chatterbox.controller.ChatterBoxController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting ChatterBox 1.0...");
        ChatterBoxController controller = new ChatterBoxController();
        controller.start();
    }
}
