"""
Ananya Shreya Soni
CSE 160
Homework 6
3/08/2024
This program analyzes Iran election data to determine
whether the election was likely fraudelent or not.
"""
import utils  # noqa: F401, do not remove if using a Mac
# add your imports BELOW this line
import csv
import random
import matplotlib.pyplot as plt


def ones_and_tens_digit_histogram(numbers):
    '''
    Input:
        a list of numbers.
    Returns:
        a list where the value at index i is the frequency in which digit i
        appeared in the ones place OR the tens place in the input list. This
        returned list will always have 10 numbers (representing the frequency
        of digits 0 - 9).

    For example, given the input list
        [127, 426, 28, 9, 90]
    This function will return
        [0.2, 0.0, 0.3, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.2]

    That is, the digit 0 occurred in 20% of the one and tens places; 2 in 30%
    of them; 6, 7, and 8 each in 10% of the ones and tens, and 9 occurred in
    20% of the ones and tens.

    See fraud_detection_tests.py for additional cases.
    '''
    histogram = [0] * 10

    # first fill histogram with counts
    for i in numbers:
        # 1's place
        histogram[i % 10] += 1

        # 10's place
        histogram[i // 10 % 10] += 1

    # normalize over total counts
    for i in range(len(histogram)):
        histogram[i] /= len(numbers) * 2

    return histogram


def extract_election_votes(filename, column_names):
    '''
    Input:
        a filename and a list of column names
    Returns:
        a list of integers that contains the values in
        those columns from every row

    '''
    election_votes = []
    csv_file = open(filename)
    csv_dict = csv.DictReader(csv_file)
    # iterate through each row in the csv file
    # note each row in the csv file is a dictionary in
    # the list of dictionaries created by csv dict reader
    for row in csv_dict:
        # iterate through the list of column names
        for next_col_name in column_names:
            # for each column name check that the the
            # next row / dictionary contains the next
            # column name as a key
            if next_col_name in row:
                # if it does then add the value mapped to
                # the key to the list we return
                col_value = row[next_col_name]  # currently a string
                col_value = col_value.replace(",", "")  # remove commas
                col_value = int(col_value)  # convert to integer
                election_votes.append(col_value)  # add to election_votes list
    csv_file.close()
    return election_votes


def plot_iran_least_digits_histogram(histogram):
    '''
    Input:
        a histogram (as created by ones_and_tens_digit_histogram)
    Returns:
        None
    Behavior:
        graphs the frequencies of the ones and tens digits for the Iranian
        election data, and saves the plot to a file named iran-digits.png

    '''
    # get the data
    list_digits = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]  # x values [0-9]
    ideal = create_uniform_dist(10)  # y values
    # plot
    plt.plot(list_digits, ideal, label="ideal")
    plt.plot(list_digits, histogram, label="iran")
    # title + labels
    plt.title("Distribution of the last two digits in Iranian dataset")
    plt.ylabel('Frequency')
    plt.xlabel('Digit')
    plt.legend(loc='upper left')
    # save the graph
    plt.savefig('iran-digits.png')
    plt.clf()


def plot_dist_by_sample_size():
    '''
    Input:
        None
    Returns:
        None
    Behavior:
        creates five different collections (sizes 10, 50, 100, 1000 and 10,000)
        of random numbers where every element in the collection is a different
        random number x such that 0 <= x <= 99, plots the ones and tens
        digits histograms for each of those collections on one graph, and
        saves the plot to a file named random-digits.png

    '''
    # get the data
    list_digits = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]  # x values [0-9]
    ideal = create_uniform_dist(10)  # y values
    plt.plot(list_digits, ideal, label="ideal")  # plot ideal
    # plot data for random frequencies of ones and tens
    # digits
    # plot the ones and tens digits histograms for each of
    # the random collections of sizes 10, 50, 100, 1000, and 10,000
    for size_of_hist in [10, 50, 100, 1000, 10000]:
        next_hist = (ones_and_tens_digit_histogram(produce_random_nums
                                                   (size_of_hist, 0, 99)))
        plt.plot(list_digits, next_hist, label=(str(size_of_hist)
                                                + " random numbers"))
    # title + labels
    plt.title("Distribution of last two digits in randomly generated samples")
    plt.ylabel('Frequency')
    plt.xlabel('Digit')
    plt.legend(loc='upper left')
    # save the graph
    plt.savefig('random-digits.png')
    plt.clf()


def produce_random_nums(amount, min, max):
    '''
    Input:
        amount of random numbers to produce and length of list returned
        min is the minimum possible integer random value inclusive
        max is the maximum possible integer random value inclusive
    Returns:
        a list of random numbers of size amount with random numbers in the
        range of min and max inclusive

    '''
    random_nums = []
    for i in range(amount):
        random_nums.append(random.randint(min, max))
    return random_nums


def mean_squared_error(numbers1, numbers2):
    '''
    Input:
        takes two non-empty lists of numbers of the same size
    Returns:
        the mean squared error between the lists or in other words
        the average squared difference between the values in
        the two lists.

    '''
    # iterate through the length of one of the lists
    total_error = 0
    for i in range(len(numbers1)):
        # keep track of total error
        total_error += ((numbers1[i] - numbers2[i]) ** 2)
    # return the mean squared error
    return total_error / len(numbers1)


def create_uniform_dist(sample_size):
    '''
    Input:
        sample_size: the size of the sample for which this method
        creates a uniform distribution for
    Returns:
        a list of the same size as the sample size whose values
        are all the same and add up to 1. The list of values represents
        a uniform distribution for the provided sample size

    '''
    uniform_dist = []
    for i in range(sample_size):
        uniform_dist.append(1 / sample_size)
    return uniform_dist


def calculate_mse_with_uniform(histogram):
    '''
    Input:
        takes a histogram (for example, as created by
        ones_and_tens_digit_histogram)
    Returns:
        the mean squared error between the given histogram
        and the uniform distribution.

    '''
    uniform_dist = create_uniform_dist(10)
    return mean_squared_error(uniform_dist, histogram)


def compare_iran_mse_to_samples(iran_mse, number_of_iran_datapoints):
    '''
    Input:
        the Iranian MSE (as computed by calculate_mse_with_uniform())
        and the number of data points in the Iranian dataset
    Returns:
        None
    Behavior:
        builds 10,000 groups of random numbers, where each number x is randomly
        generated such that 0 <= x <= 99, and each group is the same size as
        the Iranian election data, computes the MSE with the uniform
        distribution for each of the 10,000 groups, compares the 10,000 MSEs
        to the Iranian MSE determining and printing how many of the 10,000
        random MSEs are larger than or equal to the Iran MSE, how many of the
        10,000 random MSEs are smaller than the Iran MSE, and the Iranian
        election null hypothesis rejection level.

    '''
    num_groups = 10000
    larger_or_equal_mse = 0
    smaller_mse = 0
    # build 10,000 groups of random numbers
    # and for each group compute the mse with uniform
    # distribution
    for i in range(num_groups):
        next_list_random_nums = produce_random_nums(number_of_iran_datapoints,
                                                    0, 99)
        next_mse = calculate_mse_with_uniform(ones_and_tens_digit_histogram(
            next_list_random_nums))
        # next mse greater than or equal to iranian mse
        if next_mse >= iran_mse:
            larger_or_equal_mse += 1
        # next mse smaller than iranian mse
        else:
            smaller_mse += 1
    # calculate the null hypothesis rejection level
    p_value = larger_or_equal_mse / num_groups
    # print results
    print("Quantity of MSEs larger than or equal "
          + "to the 2009 Iranian election MSE: "
          + str(larger_or_equal_mse))
    print("Quantity of MSEs smaller than the 2009 Iranian election MSE: "
          + str(smaller_mse))
    print("2009 Iranian election null hypothesis rejection level p: "
          + str(p_value))


def main():
    # extract election votes using the extract election votes for
    # all 4 candidates
    election_votes = extract_election_votes("election-iran-2009.csv",
                                            ["Ahmadinejad", "Rezai", "Karrubi",
                                                "Mousavi"])
    # use the provided ones_and_tens_digit_histogram method to
    # produce list of frequencies of ones and tens digits
    election_votes_histogram = ones_and_tens_digit_histogram(election_votes)
    # plot the iran digits histogram
    plot_iran_least_digits_histogram(election_votes_histogram)
    # plot the distribution of the last two digits in randomly generated
    # samples
    plot_dist_by_sample_size()
    iran_mse = calculate_mse_with_uniform(election_votes_histogram)
    # print iranian mse
    print("2009 Iranian election MSE: " + str(iran_mse))
    # compare the iranian mse to the random samples mses
    compare_iran_mse_to_samples(iran_mse, len(election_votes))


if __name__ == "__main__":
    main()
