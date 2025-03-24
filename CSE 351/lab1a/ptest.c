/*
 * Test harness for pointer.c
 *
 * By: Dustin Lundquist <dol@cs.washington.edu>
 */

#include <stdio.h>
#include "pointer.h"
#include "common.h"

typedef struct test_s {
    char *function_name;
    int (*function_under_test)();
    int expected_result;
} test;

typedef struct within_same_block_args_s {
    char * function_name;
    int * ptr1;
    int * ptr2;
    int expected_result;
} within_same_block_args;

typedef struct within_array_args_s {
    char * function_name;
    int * base;
    int size;
    int * ptr;
    int expected_result;
} within_array_args;

typedef struct swap_ints_args_s {
    char * function_name;
    int * ptr1;
    int * ptr2;
} swap_ints_args;

typedef struct string_length_args_s {
    char * function_name;
    char * s;
    int expected_result;
} string_length_args;

typedef struct endian_experiment_args_s {
    char * function_name;
    int * s;
    int expected_result;
} endian_experiment_args;

typedef struct selection_sort_args_s {
    char * function_name;
    int * arr;
    int * arr_copy;
    int arrLength;
    int * expected_result;
} selection_sort_args;

static test tests[] = {
    { "int_size()",      int_size,        sizeof(int) },
    { "double_size()",   double_size,     sizeof(double) },
    { "pointer_size()",  pointer_size,    sizeof(void *) },
    { NULL, NULL, 0 },
    { "change_value()",  change_value,    351 },
    { NULL, NULL, 0 }
};

static within_same_block_args box_args[] = {
  {"within_same_block", (int *) 0x1,        (int *) 72,          0},
  {"within_same_block", (int *) 0x1,        (int *) 4,           1},
  {"within_same_block", (int *) 0x12345678, (int *) 0x1,         0},
  {"within_same_block", (int *) 0x12345678, (int *) 0x12345658,  1},
  {"within_same_block", (int *) 0x12345678, (int *) 0x12345686,  0},
  {NULL, NULL, NULL, 0}
};

static within_array_args arr_args[] = {
  {"within_array", (int *) 0x1, 4, (int *) 0xd, 1}, 
  {"within_array", (int *) 0x1, 4, (int *) 0x11, 0}, 
  {"within_array", (int *) 0x14, 4, (int *) 0xd, 0}, 
  {"within_array", (int *) 0x1, 8, (int *) 0x20, 1},
  {NULL, NULL, 0, NULL, 0}
};

static int int1 = 1;
static int int2 = 2;
static swap_ints_args swap_args[] = {
  { "swap_ints", &int1, &int2 },
  { NULL, NULL, NULL }
};

static char string1[] = "hello";
static char string2[] = "hello world";
static char string3[] = "";
static string_length_args strlen_args[] = {
  { "string_length", (char*)string1, 5 },
  { "string_length", (char*)string2, 11 },
  { "string_length", (char*)string3, 0 },
  { NULL, NULL, 0 }
};

static int ptr = 0;
static endian_experiment_args endianExp_args[] = {
  { "endian_experiment()" , &ptr , 351351},
  { NULL, NULL, 0 }
};

static int arr1[] = {2, 1};
static int arr1_copy[] = {2, 1};
static int expected_arr1[] = {1, 2};
static int arr2[] = {5, 2, 4, 3, 1};
static int arr2_copy[] = {5, 2, 4, 3, 1};
static int expected_arr2[] = {1, 2, 3, 4, 5};
static selection_sort_args selSort_args[] = {
  { "selection_sort" , arr1 , arr1_copy , 2, expected_arr1 },
  { "selection_sort" , arr2 , arr2_copy , 5, expected_arr2 },
  { NULL, NULL, 0 }
};

int main(int argc, char **argv) {
    int result;
    int score = 0;
    test * t;

    for (t = tests; t->function_name; t++) {
        result = t->function_under_test();

        printf("%s = %d", t->function_name, result);

        if (result == t->expected_result) {
            printf("\t[ OK ]\n");
            score ++;
        } else {
            printf("\t[ fail ] (expected %d)\n", t->expected_result);
        }
    }

    swap_ints_args * args3;

    for(args3 = swap_args; args3->function_name != NULL; args3++) {
      int ptr1PrevVal = *(args3->ptr1);
      int ptr2PrevVal = *(args3->ptr2);
      printf("%s(*ptr1=%d, *ptr2=%d) = ", args3->function_name, *args3->ptr1, *args3->ptr2);
      swap_ints(args3->ptr1, args3->ptr2);
      printf("*ptr1=%d, *ptr2=%d", *args3->ptr1, *args3->ptr2);
      if (*(args3->ptr1) == ptr2PrevVal && *(args3->ptr2) == ptr1PrevVal) {
        printf("\t[ OK ]\n");
      } else {
        printf("\t[ fail ] (expected *ptr1=%d, *ptr2=%d)\n", ptr2PrevVal, ptr1PrevVal);
      }
    }

    for (t++; t->function_name; t++) {
        result = t->function_under_test();

        printf("%s = %d", t->function_name, result);

        if (result == t->expected_result) {
            printf("\t[ OK ]\n");
            score ++;
        } else {
            printf("\t[ fail ] (expected %d)\n", t->expected_result);
        }
    }

    within_same_block_args * args;

    for (args = box_args; args->function_name != NULL; args++) {
        result = within_same_block(args->ptr1, args->ptr2);
        printf("%s(%p, %p) = %d", args->function_name, args->ptr1, args->ptr2, result);
        if (result == args->expected_result) {
          printf("\t[ OK ]\n");
        } else {
          printf("\t[ fail ] (expected %d)\n", args->expected_result);
        }
    }

    within_array_args * args2;

    for (args2 = arr_args; args2->function_name != NULL; args2++) {
      result = within_array((args2->base), args2->size, args2->ptr);
      printf("%s(%p, %d, %p) = %d", args2->function_name, args2->base, args2->size, args2->ptr, result);
      if (result == args2->expected_result) {
        printf("\t[ OK ]\n");
      } else {
        printf("\t[ fail ] (expected %d)\n", args2->expected_result);
      }
    }

    string_length_args * args4;

    for(args4 = strlen_args; args4->function_name != NULL; args4++) {
        result = string_length(args4->s);
        printf("%s(%s) = %d", args4->function_name, args4->s, result);
        if (result == args4->expected_result) {
          printf("\t[ OK ]\n");
        } else {
          printf("\t[ fail ] (expected %d)\n", args4->expected_result);
        }
    }

    endian_experiment_args * args5;

    for(args5 = endianExp_args; args5->function_name != NULL; args5++) {
        result = endian_experiment(args5->s);
        printf("%s = %d", args5->function_name, result);
        if (result == args5->expected_result) {
          printf("\t[ OK ]\n");
        } else {
          printf("\t[ fail ] (expected %d)\n", args5->expected_result);
        }
    }

    selection_sort_args * args6;

    for(args6 = selSort_args; args6->function_name != NULL; args6++) {
        selection_sort(args6->arr, args6->arrLength);

        printf("%s(", args6->function_name);
        print_array( args6->arr_copy, args6->arrLength);
        printf(", %d) = ", args6->arrLength);
        print_array( args6->arr, args6->arrLength);
        int i;
        result = 1;
        for(i = 0; i < args6->arrLength; i++) {
          if(args6->arr[i] != args6->expected_result[i]) {
            result = !result;
            break;
          }
        }
        if (result) {
          printf("\t[ OK ]\n");
        } else {
          printf("\t[ fail ] (expected ");
          print_array( args6->expected_result, args6->arrLength);
          printf(")\n");
        }
    }

    return 0;
}
