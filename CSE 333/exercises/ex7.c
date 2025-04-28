// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/20/2025
// ex7

// Prints the contents of the .txt files in the
// provided directory to stdout

#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>  // for open()
#include <unistd.h>  // for close()
#include <dirent.h>
#include <errno.h>
#include <string.h>

#define SIZE 1

// Behavior:
//   -  Prints the contents of a file to stdout
//      given the path to the file
// Parameters:
//   - char* filepath: path to file
static void PrintFileContents(char* filepath);

int main(int argc, char** argv) {
  // Check to make sure we have valid command line arguments
  if (argc != 2) {
    fprintf(stderr, "Usage: ./ex7 <directory path>\n");
    return EXIT_FAILURE;
  }
  // Open the directory
  DIR* dirp = opendir(argv[1]);
  if (dirp == NULL) {
    fprintf(stderr, "Could not open directory\n");
    return EXIT_FAILURE;
  }
  // Read through/parse the directory and print out file
  // contents of files ending in .txt
  struct dirent *entry;
  entry = readdir(dirp);
  while (entry != NULL) {
    char* filename = entry->d_name;
    entry = readdir(dirp);
    size_t len = strlen(filename);
    if (len >= 4 && strcmp(filename + len - 4, ".txt") == 0) {
      char* filepath = (char*) malloc(len + strlen(argv[1]) + 2);
      int cx = snprintf(filepath, len + strlen(argv[1]) + 2,
                        "%s/%s", argv[1], filename);
      if (cx < 0 && cx >= len + strlen(argv[1]) + 2) {
        printf("file path failed to be completely written by snprintf\n");
        free(filepath);
        return EXIT_FAILURE;
      }
      PrintFileContents(filepath);
      free(filepath);
    }
  }
  // Clean up
  closedir(dirp);
  return EXIT_SUCCESS;
}

static void PrintFileContents(char* filepath) {
  // Open the file, use O_RDONLY flag
  int fd = open(filepath, O_RDONLY);
  if (fd == -1) {
    fprintf(stderr, "Could not open file for reading\n");
    return;
  }
  // Read from the file and write its contents to standard out.
  char buf[SIZE];
  ssize_t bytesRead;
  while ((bytesRead = read(fd, buf, SIZE)) > 0) {
    if (fwrite(buf, 1, bytesRead, stdout) < bytesRead) {
      perror("fwrite failed\n");
      break;
    }
  }
  // Test to see if we encountered an error while reading
  if (bytesRead == -1) {
    perror("read failed\n");
  }
  // Clean up
  close(fd);
  return;
}
