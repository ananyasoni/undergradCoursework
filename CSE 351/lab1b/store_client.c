/*
 * CSE 351 Lab 1b (Manipulating Bits in C)
 *
 * Name(s): Ananya Soni  
 * NetID(s): ananya99
 *
 * This is a file for managing a store of various aisles, represented by an
 * array of 64-bit integers. See aisle_manager.c for details on the aisle
 * layout and descriptions of the aisle functions that you may call here.
 *
 * Written by Porter Jones (pbjones@cs.washington.edu)
 */

#include <stddef.h>  // To be able to use NULL
#include "aisle_manager.h"
#include "store_client.h"
#include "store_util.h"

// Number of aisles in the store
#define NUM_AISLES 10

// Number of sections per aisle
#define SECTIONS_PER_AISLE 4

// Number of items in the stockroom (2^6 different id combinations)
#define NUM_ITEMS 64

// Global array of aisles in this store. Each unsigned long in the array
// represents one aisle.
unsigned long aisles[NUM_AISLES];

// Array used to stock items that can be used for later. The index of the array
// corresponds to the item id and the value at an index indicates how many of
// that particular item are in the stockroom.
int stockroom[NUM_ITEMS];


/* Starting from the first aisle, refill as many sections as possible using
 * items from the stockroom. A section can only be filled with items that match
 * the section's item id. Prioritizes and fills sections with lower addresses
 * first. Sections with lower addresses should be fully filled (if possible)
 * before moving onto the next section.
 */
void refill_from_stockroom() {
  // iterate through aisles in the store starting 
  // from the first aisle
  for (int i = 0; i < NUM_AISLES; i++) {
    unsigned long next_aisle = aisles[i];
    // iterate through sections in the next aisle 
    for (int j = 0; j < SECTIONS_PER_AISLE; j++) {
      // get item id for section
      unsigned short id = get_id(&next_aisle, j);
      // check how many items with item_id are in the stock room
      int stock = stockroom[id];
      // check how many empty spaces there are in the section
      int num_spaces = 10 - num_items(&next_aisle, j);
      if (stock < num_spaces) {
        stockroom[id] = 0;
        add_items(&next_aisle, j, stock);
      } else {
        stockroom[id] -= num_spaces;
        add_items(&next_aisle, j, num_spaces);
      }
    }
    aisles[i] = next_aisle;
  }

}

/* Remove at most num items from sections with the given item id, starting with
 * sections with lower addresses, and return the total number of items removed.
 * Multiple sections can store items of the same item id. If there are not
 * enough items with the given item id in the aisles, first remove all the
 * items from the aisles possible and then use items in the stockroom of the
 * given item id to finish fulfilling an order. If the stockroom runs out of
 * items, you should remove as many items as possible.
 */
int fulfill_order(unsigned short id, int num) {
  int num_items_removed = 0;
  // iterate through aisles in the store starting
  // from the first aisle
  // check how many items with id are in the stock room
  int stock = stockroom[id];
  for (int i = 0; i < NUM_AISLES && num_items_removed <= num; i++) {
    unsigned long next_aisle = aisles[i];
    // iterate through sections in the next aisle starting with 
    // the first section at the lowest address
    for (int j = 0; j < SECTIONS_PER_AISLE && num_items_removed <= num; j++) {
      // get item id for section
      unsigned short section_id = get_id(&next_aisle, j);
      // check if section_id matches id
      if (section_id == id) {
        // check how many items there are in the current section
        int amt_items = num_items(&next_aisle, j);
        if (amt_items + num_items_removed > num) {
          // only remove num - num_items_removed
          remove_items(&next_aisle, j, num - num_items_removed);
          num_items_removed += (num - num_items_removed);
          aisles[i] = next_aisle;
          return num_items_removed;
        } else {
          remove_items(&next_aisle, j, amt_items);
          num_items_removed += amt_items;
          aisles[i] = next_aisle;
        }
      }
    }
  }
  if (num_items_removed < num) {
    // remove items from stockroom
    if (stock + num_items_removed > num) {
      // only remove num - num_items_removed from the stockroom
      stockroom[id] -= num - num_items_removed;
      num_items_removed += (num - num_items_removed);;
    } else {
      stockroom[id] = 0;
      num_items_removed += stock;
    }
    return num_items_removed;
  }
  return num_items_removed;
}

/* Return a pointer to the first section in the aisles with the given item id
 * that has no items in it or NULL if no such section exists. Only consider
 * items stored in sections in the aisles (i.e., ignore anything in the
 * stockroom). Break ties by returning the section with the lowest address.
 */
unsigned short* empty_section_with_id(unsigned short id) {
  // iterate through aisles in the store starting 
  // from the first aisle  
  for (int i = 0; i < NUM_AISLES; i++) {
    unsigned long next_aisle = aisles[i];
    // iterate through sections in the next aisle starting with
    // the first section at the lowest address
    for (int j = 0; j < SECTIONS_PER_AISLE; j++) {
      unsigned short section_id = get_id(&next_aisle, j);
      if (section_id == id) { 
        int num_spaces = 10 - num_items(&next_aisle, j);
        if (num_spaces == 10) {
          return (unsigned short*)(&aisles[i]) + j;
        }
      }
    } 
  }
  return NULL;
}

/* Return a pointer to the section with the most items in the store. Only
 * consider items stored in sections in the aisles (i.e., ignore anything in
 * the stockroom). Break ties by returning the section with the lowest address.
 */
unsigned short* section_with_most_items() {
  unsigned short* res = (unsigned short*)(&aisles[0]) + 0;
  int max_items = num_items(&aisles[0], 0);
  // iterate through aisles in the store starting
  // from the first aisle
  for (int i = 0; i < NUM_AISLES; i++) {
    unsigned long next_aisle = aisles[i];
    // iterate through sections in the next aisle starting with
    // the first section at the lowest address
    for (int j = 0; j < SECTIONS_PER_AISLE; j++) {
      int amt_items = num_items(&next_aisle, j);
      if (amt_items > max_items) {
        max_items = amt_items;
        res = (unsigned short*)(&aisles[i]) + j;
      }
    }
  } 
  return res;
}
