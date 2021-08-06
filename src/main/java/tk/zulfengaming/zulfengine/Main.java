package tk.zulfengaming.zulfengine;

import tk.zulfengaming.zulfengine.game.Game;

public class Main {

    public static void main(String[] args) {

       new Thread(() -> {
           Game gameInstance = new Game();
           gameInstance.run();
       }).start();

    }


}
