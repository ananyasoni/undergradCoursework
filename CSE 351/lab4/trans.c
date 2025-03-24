/*
 * CSE 351 Lab 4 (Caches and Cache-Friendly Code)
 * Part 2 - Optimizing Matrix Transpose
 *
 * Name(s): Ananya Shreya Soni
 * NetID(s): ananya99
 *
 * Each transpose function must have a prototype of the form:
 * void trans(int M, int N, int A[M][N], int B[N][M]);
 * and compute B = A^T.
 *
 * A transpose function is evaluated by counting the number of misses
 * on a 1 KiB direct mapped cache with a block size of 32 bytes.
 */

 #include <stdio.h>
 #include "support/cachelab.h"

 int is_transpose(int M, int N, int A[M][N], int B[N][M]);

 /*
  * transpose_submit - This is the transpose function that you will be graded
  *     on. Do not change the description string "Transpose submission", as the
  *     driver searches for that string to identify the transpose function to be
  *     graded.
  */
char transpose_submit_desc[] = "Transpose submission";
void transpose_submit(int M, int N, int A[M][N], int B[N][M]) {
    if (M == 32 && N == 32) {
        int row[8];
        for (int i = 0; i < M / 8; i++) {
            for (int j = 0; j < M / 8; j++) {
                for (int s1 = 0; s1 < 8; s1++) {
                    for (int s2 = 0; s2 < 8; s2++) {
                        row[s2] = A[8 * i + s1][8 * j + s2];
                    }
                    for (int s2 = 0; s2 < 8; s2++) {
                        B[8 * j + s2][8 * i + s1] = row[s2];
                    }
                }
            }
        }
    } else {
    int row[4];
        for (int i = 0; i < M / 4; i++) {
            for (int j = 0; j < M / 4; j++) {
                for (int s1 = 0; s1 < 4; s1++) {
                    for (int s2 = 0; s2 < 4; s2++) {
                        row[s2] = A[4 * i + s1][4 * j + s2];
                    }
                    for (int s2 = 0; s2 < 4; s2++) {
                        B[4 * j + s2][4 * i + s1] = row[s2];
                    }
                }
            }
        }
    }
}


 // You can define additional transpose functions below. We've defined a simple
 // one below to help you get started.
 /*
  * trans - A simple baseline transpose function, not optimized for the cache.
  */
 char trans_desc[] = "Simple row-wise scan transpose";
 void trans(int M, int N, int A[M][N], int B[N][M]) {
     int i, j, tmp;
     for (i = 0; i < M; i++) {
         for (j = 0; j < N; j++) {
             tmp = A[i][j];
             B[j][i] = tmp;
         }
     }
 }

 /*
  * registerFunctions - This function registers your transpose
  *     functions with the driver.  At runtime, the driver will
  *     evaluate each of the registered functions and summarize their
  *     performance. This is a handy way to experiment with different
  *     transpose strategies.
  */
 void registerFunctions() {
     /* Register your solution function */
     registerTransFunction(transpose_submit, transpose_submit_desc);
     /* Register any additional transpose functions */
     registerTransFunction(trans, trans_desc);
 }
 /*
  * is_transpose - This helper function checks if B is the transpose of
  *     A. You can check the correctness of your transpose by calling
  *     it before returning from the transpose function.
  */
 int is_transpose(int M, int N, int A[M][N], int B[N][M]) {
     int i, j;
     for (i = 0; i < M; i++) {
         for (j = 0; j < N; ++j) {
             if (A[i][j] != B[j][i]) {
                 return 0;
             }
         }
     }
     return 1;
 }
