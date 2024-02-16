# Batuino
## Embed a batch script in an arduino Keyboard sketch

> This project has two flavors, one in Java, and one in Python.

## Usage:
### Python
```console
python batuino.py -i test.bat -o test.ino
```

### Java:
```console
java batuino.jar // will ask for file name
```

Open the arduino sketch in the Arduino IDE and upload to a HID compatible board such as the Arduino Pro Micro.

I use this with an Arduino Pro Micro, aka Leonardo

When you plug your arduino in to your computer, the batch script should automaticaly run.

## Todo
-   get shell scripts working