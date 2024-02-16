import sys
import argparse

def main():
    parser = argparse.ArgumentParser(description="Convert Batch script to Arduino .ino file")
    parser.add_argument("-i", "--input", help="Input Batch script file", required=True)
    parser.add_argument("-o", "--output", help="Output Arduino .ino file", required=True)
    args = parser.parse_args()

    input_file = args.input
    output_file = args.output

    # template C file
    out_str = """#include <Keyboard.h>

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
  delay(delayAmount);
"""

    # read each line of the batch file and add "doCommand" function to it
    with open(input_file) as inputfile:
        for line in inputfile:
            line = line.replace("\n", "")
            line = line.replace("\"", "\\\"")
            out_str += "  doCommand(\"%s\");\n" % line

    # concatenate the last part of the code
    out_str += """  doCommand(\"exit\");
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
    with open(output_file, "w") as fileout:
        fileout.write(out_str)

if __name__ == "__main__":
    main()
