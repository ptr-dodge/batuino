import sys

# template C file
outStr = """#include <Keyboard.h>

int delayAmount = 600;

void typeKey(int key)
{
  Keyboard.press(key);
  delay(50);
  Keyboard.release(key);
}

void doCommand(char command[])
{
  Keyboard.print(command);
  Keyboard.press(KEY_RETURN);
  Keyboard.releaseAll();
  delay(delayAmount);
}

void opencmd()
{
  typeKey(KEY_ESC);
  Keyboard.press(KEY_LEFT_CTRL);
  Keyboard.press(KEY_ESC);
  Keyboard.releaseAll();
  delay(delayAmount);
  Keyboard.print("cmd");
  delay(delayAmount);
  typeKey(KEY_RETURN);
"""

# read each line of the batch file and add "doCommand" function to it
with open(sys.argv[1]) as inputfile:
    for line in inputfile:
        line = line.replace("\n", "")
        outStr += "  doCommand(\"%s\");\n" % line

# concatenate the last part of the code
outStr += """  doCommand("exit");
}

void setup()
{
  Keyboard.begin();
  delay(1000);
  opencmd();
}

void loop() {}
"""

# open and write to the output file
fileout = open("batuino_output.ino", "w")
fileout.write(outStr)
fileout.close()
