"""
*Name: Ananya Soni
*Date: 1/13/2024
*CSE 160, Winter 2024
*Homework 1
*Description:
"""

# Uncomment the line below to make the math.sqrt function available
import math

# Problem 1
"""
for any quadratic eq in the form ax^2 + bx + c = 0
roots given by formulas: ((-b + sqrt(b^2 - 4ac))/2a)
                         ((-b - sqrt(b^2 - 4ac))/2a)
"""
print("Problem 1 solution follows:")
a = 3
b = -5.86
c = 2.5408
# root 1 given by ((-b - sqrt(b^2 - 4ac))/2a)
print("Root 1:", ((-b) - (math.sqrt((b * b) - (4 * a * c))))/(2 * a))
# root 2 given by ((-b + sqrt(b^2 - 4ac))/2a)
print("Root 2:", ((-b) + (math.sqrt((b * b) - (4 * a * c))))/(2 * a))
print()

# Problem 2
"""
prints fractions and their decimal form from
1/2 all the way to 1/10
"""
print("Problem 2 solution follows:")
for i in [2, 3, 4, 5, 6, 7, 8, 9, 10]:
    print("1/" + str(i) + ": " + str(1/i))
print()

# Problem 3
# prints triangular number for n
print("Problem 3 solution follows:")

# Provided partially-working solution to problem 3
# `...` are placeholders and should be replaced
n = 10
triangular = 0
for i in range(1, n + 1):
    triangular = triangular + i
print("Triangular number", n, "via loop:", triangular)
print("Triangular number", n, "via formula:", n * (n + 1) / 2)
print()

# Problem 4
# finds and prints the factorial of n
n = 10
factorial = 1
for i in range(1, n + 1):
    factorial = factorial * i
print("Problem 4 solution follows:")
print(str(n) + "!: " + str(factorial))
print()

# Problem 5
# finds and prints num_lines to 1 factorials
num_lines = 10
print("Problem 5 solution follows:")
for i in range(num_lines, 0, -1):
    factorial = 1
    for j in range(1, num_lines + 1):
        factorial = factorial * j
    print(str(num_lines) + "!: " + str(factorial))
    num_lines = num_lines - 1
print()

# Problem 6
# finds the sums of reciprocals of n factorials
print("Problem 6 solution follows:")
sum = 1
n = 10
for i in range(n, 0, -1):
    factorial = 1
    for j in range(1, n + 1):
        factorial = factorial * j
    sum += (1/(factorial))
    n = n - 1
print("e: " + str(sum))
