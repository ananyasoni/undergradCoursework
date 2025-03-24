/*
 * CSE 351 Lab 5 (Dynamic Storage Allocator)
 * Extra credit: Implementing mm_realloc
 *
 * Name(s):  
 * NetID(s): 
 *
 * NOTES:
 *  - This requires mm_malloc and mm_free to be working correctly, so don't
 *    start on this until you finish mm.c.
 *  - Only the traces that start with 'realloc' actually call this function,
 *    so make sure you actually test your code on these traces!
 *  - This file does not need to be submitted if you did not attempt it.
 */
#include "mm.c"


/*
 * EXTRA CREDIT:
 * Change the size of the memory block pointed to by ptr to size bytes while
 * preserving the existing data in range. If a new block is allocated during
 * this process, make sure to free the old block.
 *  - if ptr is NULL, equivalent to malloc(size)
 *  - if size is 0, equivalent to free(size)
 */
void* mm_realloc(void* ptr, size_t size) {
  // TODO: Implement mm_realloc.
  // You will want to replace this return statement...
  return NULL;
}
