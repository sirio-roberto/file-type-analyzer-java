# File Analyzer
File Analyzer is a Java program designed to search for specific patterns within files in a given folder. It utilizes multi-threading to efficiently process files and identify patterns using various string searching algorithms.

## Features
* Multi-threaded file pattern matching.
* Support for searching multiple patterns simultaneously.
* Customizable patterns and their priorities.
* String searching algorithms like Rabin-Karp and Knuth-Morris-Pratt.

## Getting Started
### Prerequisites
* Java Development Kit (JDK) 8 or higher.
* Command-line interface (CLI).

### Installation
1. Clone the repository to your local machine:
   ```shell
   git clone https://github.com/yourusername/file-analyzer.git
    ```
2. Navigate to the project directory:
    ```shell
   cd file-analyzer
    ```
3. Compile the source code:
    ```shell
   javac analyzer/*.java
    ```
## Usage
To use the File Analyzer, follow these steps:

1. Open a terminal window.
2. Navigate to the directory where you compiled the program.
3. Run the program with the following command:
    ```
   java analyzer.Main [folderPath] [patternsPath]
   ```
   Replace `[folderPath]` with the path to the folder containing the files you want to analyze and `[patternsPath]` with the path to the file containing the patterns to search for. 
   Example:
   ```shell
    java analyzer.Main /path/to/folder /path/to/patterns.txt
   ```

4. The program will search for the specified patterns in the files within the provided folder and display the results.

## Customizing Patterns
You can customize the patterns to search for by editing the `patterns.txt` file. Each line in the file should follow this format:
```shell
priority;pattern;text
```
* `priority`: An integer indicating the priority of the pattern.
* `pattern`: The pattern to search for.
* `text`: The type or description of the pattern.

Example `patterns.txt`:
```
1;"%PDF-";"PDF document"
2;"pmview";"PCP pmview config"
4;"PK";"Zip archive"
5;"vnd.oasis.opendocument.presentation";"OpenDocument presentation"
6;"W.o.r.d";"MS Office Word 2003"
6;"P.o.w.e.r.P.o.i";"MS Office PowerPoint 2003"
```

### Happy file analyzing!