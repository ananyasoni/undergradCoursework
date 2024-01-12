# Ananya Soni
# CSE 160
# Winter 2024
# Practice #1


# Problem 1
def sum_evens(nums):
    '''
    Takes in a list of integers and returns the sum of the even ints in the
    given list.
    Note: the % "mod" operator computes the remainder, e.g. 5 % 2 is 1.
    Arguments:
        nums: a list of integers.

    Returns: a integer representing the sum of the even numbers in the list
    '''
    # your solution code should start here
    sum = 0
    for num in nums:
        if num % 2 == 0:
            sum += num
    return sum


assert sum_evens([5, 2, 6, 3, 4]) == 12
assert sum_evens([3, 6, 11, 2, 5]) == 8
assert sum_evens([]) == 0
assert sum_evens([7]) == 0
assert sum_evens([5, 8, 5]) == 8
assert sum_evens([72, 2, 6, 1]) == 80
assert sum_evens([6]) == 6
assert sum_evens([4, 4, 4]) == 12
assert sum_evens([1, 1, 1, 2]) == 2


# Problem 2
def hidden_char(input_str, hidden_char):
    '''
    Returns True if string hidden_char is found in the given string.
    You may not use the keyword 'in'

    Arguments:
        input_str: a string
        hidden_char: the character to look for in input_str

    Returns: A boolean representing whether or not hidden_char is in input_str
    '''
    # your solution code should start here
    for char in input_str:
        if char == hidden_char:
            return True
    return False


assert hidden_char('cse160', 's') is True
assert hidden_char('c', 'c') is True
assert hidden_char('a', 'b') is False
assert hidden_char('coding', 'g') is True
assert hidden_char('I love python', 'z') is False
assert hidden_char('Snakes', 's') is True
assert hidden_char('123', 'g') is False
assert hidden_char('', 'a') is False


# Problem 3
def count_num(nums, find_num):
    '''
    Return the number of times the number find_num occurs in the given list

    Arguments:
        nums: a list of ints
        find_num: the integer to count the occurrences of in nums

    Returns:
        A integer representing the number of times find_num appears in nums
    '''
    # your solution code should start here
    numMatch = 0
    for nextNum in nums:
        if nextNum == find_num:
            numMatch += 1
    return numMatch


assert count_num([1, 5, 6, 2, 1], 1) == 2
assert count_num([], 4) == 0
assert count_num([1, 2, 3], 1) == 1
assert count_num([4, 5, 6], 2) == 0
assert count_num([160], 160) == 1
assert count_num([90], 25) == 0
assert count_num([5, 5, 5], 5) == 3
assert count_num([3, 6, 12, 9, 12], 12) == 2
