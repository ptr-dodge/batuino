package batuino;

import java.io.*;
import java.util.Scanner;

public class Batuino {

    public static void main(String[] args) {

        // default sketch name
        String fileOutName = "batuino_output.ino";

        // first part of Arduino sketch
        String inoFile1 = "#include <Keyboard.h>\n\nint delayAmount = 600;\n\nvoid typeKey(int key)\n{\n  Keyboard.press(key);\n  delay(50);\n  Keyboard.release(key);\n}\n\nvoid doCommand(char command[])\n{\n\nKeyboard.print(command);\n  Keyboard.press(KEY_RETURN);\n  Keyboard.releaseAll();\n  delay(delayAmount);\n}\n\nvoid opencmd()\n{\n  typeKey(KEY_ESC);\n  Keyboard.press(KEY_LEFT_CTRL);\n  Keyboard.press(KEY_ESC);\n  Keyboard.releaseAll();\n  delay(delayAmount);\n  Keyboard.print(\"cmd\");\n  delay(delayAmount);\n  typeKey(KEY_RETURN);";

        // last part of Arduino sketch
        String inoFile2 = "\n  doCommand(\"exit\");\n}\n\nvoid setup()\n{\n  Keyboard.begin();\n  delay(1000);\n  opencmd();\n}\n\n void loop() {} // unused";

        // variable for reading the lines of the batch file
        String lines = "";

        // read in the file name from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("------------------------------------------------");
        System.out.print("Please type the name of the batch file to embed: ");
        String fileName = scanner.nextLine();
        scanner.close();

        try {
            // create a FileReader object
            FileReader fileReader = new FileReader(fileName);
            // create a copy of the fileReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // define a variable for each line in the file
            String line;

            // read each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace("\n", ""); // replace newlines with nothing
                // wrap each line in the doCommand function in the Arduino sketch
                // so each line is executed in cmd
                // add each line to the variable 'lines'
                lines += String.format("\n  doCommand(\"%s\");", line);
            }
            // close the file
            bufferedReader.close();

        } catch (FileNotFoundException ex) {
            // error handling stuff
            System.out.println("Unable to open file '" + fileName + "'");
            System.exit(1);
        } catch (IOException ex) {
            // error handling stuff
            System.out.println("Error reading file '" + fileName + "'");
            System.exit(1);
        }

        // put together the whole file as a string
        String Final = inoFile1 + lines + inoFile2;

        // write to the output file
        try {
            // make a file writer
            FileWriter myWriter = new FileWriter(fileOutName);
            // write
            myWriter.write(Final);
            // close
            myWriter.close();
            // let the user know we've written
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            // handle errors if any
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}