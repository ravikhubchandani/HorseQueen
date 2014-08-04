package process;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.GameInformation;

public class GameFileManager {
   public static GameInformation LoadGame(String uri) {
      try {
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(uri));
         GameInformation gameInformation = (GameInformation) ois.readObject();
         ois.close();
         return gameInformation;
      }
      catch(Exception e) {
         System.out.println(e.toString());
         return null;
      }
   }
   
   public static void SaveGame(GameInformation gameInformation, String uri) {
      try {
         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(uri));
         oos.writeObject(gameInformation);
         oos.close();
      } catch(Exception e) {
         System.out.println(e.toString());
      }
   }
}
