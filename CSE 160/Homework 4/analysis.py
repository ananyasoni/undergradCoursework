"""
* Name: Ananya Shreya Soni
* Date: 02/10/2024
* CSE 160, Winter 2023
* Homework 4
* Description: This class uses the K-Means
* Clustering Algorithm and provided a data set
* finds the accuracy of the K-Means algorithm
* which is used to make predictions about the
* data set
"""

from kmeans import get_closest_centroid
from utils import load_centroids, read_data, assert_equals


def assign_update(list_of_points, labels, centroids_dict):
    """Assign all data points to the closest centroids and keep track of their
    labels. The i-th point in "data" corresponds to the i-th label in "labels".

    Arguments:
        list_of_points: a list of lists representing all data points
        labels: a list of ints representing all data labels
                labels[i] is the label of the point list_of_points[i]
        centroids_dict: a dictionary representing the centroids where the keys
                        are strings (centroid names) and the values are lists
                        of centroid locations

    Returns: a new dictionary whose keys are the centroids' key names and
             values are a list of labels of the data points that are assigned
             to that centroid.
    """
    # create a new dictionary updatedCentroids
    # itterate through the list of points
    # for each point find the closest centroid
    # add the centroid to the dictionary and add that point to the list of
    # points mapped to that centroid key
    updated_centroids = {}
    for i in range(len(list_of_points)):
        nextPoint = list_of_points[i]
        closest_centroid = get_closest_centroid(nextPoint, centroids_dict)
        # if closestCentroid dictionary does not contain closestCentroid key
        # add it to the dictionary and map it to an empty list
        if (updated_centroids.get(closest_centroid) is None):
            list_labels = []
            updated_centroids[closest_centroid] = list_labels
        # append the next label to the list associated with the closestCentroid
        # key
        # note: the append method modifies a list in-place and returns None
        # so you must call the method on the list object directly otherwise
        # you assign the list_labels value to None
        updated_centroids[closest_centroid].append(labels[i])
    return updated_centroids


def majority_count(labels):
    """Return the count of the majority labels in the label list

    Arguments:
        labels: a list of labels

    Returns: the count of the majority labels in the list
    """
    # create a dictionary with the key being the label and the
    # value being the frequency of that key (label) appearing in the list
    label_to_counts = {}
    # itterate through the list of labels
    for label in labels:
        if label not in label_to_counts:
            label_to_counts[label] = 0
        label_to_counts[label] = label_to_counts[label] + 1
    # dictionary now contains all labels and their corresponding frequency
    # itterate through the dictionary
    # store current max_frequency as a variable
    max_frequency = -1  # max frequency
    for next_label in label_to_counts:
        if label_to_counts[next_label] > max_frequency:
            max_frequency = label_to_counts[next_label]
    return max_frequency


def accuracy(list_of_points, labels, centroids_dict):
    """Calculate the accuracy of the algorithm. You should use
    assign_update and majority_count (that you previously implemented)

    Arguments:
        list_of_points: a list of lists representing all data points
        labels: a list of ints representing all data labels
                labels[i] is the label of the point list_of_points[i]
        centroids_dict: a dictionary representing the centroids where the keys
                        are strings (centroid names) and the values are lists
                        of centroid locations

    Returns: a float representing the accuracy of the algorithm
    """
    # first assign_update to centroids and retrieve dictionary mapping
    # centroids to labels
    centroids_to_labels = assign_update(list_of_points, labels, centroids_dict)
    # keep track of sum  of count of all the majority labels across the
    # clusters
    count_of_majority_labels = 0
    # the total number of labels equals the length of the labels list
    total_num_labels = len(labels)
    # iterate through the centroids_to_labels dictionary and keep updating the
    # count_of_majority_labels counter
    for centroid in centroids_to_labels:
        next_list_of_labels = centroids_to_labels[centroid]  # value
        next_majority_count = majority_count(next_list_of_labels)
        # print(centroid + " " + str((next_majority_count)
        # / (len(next_list_of_labels))))
        # next_accuracy = ((next_majority_count) / (len(next_list_of_labels)))
        count_of_majority_labels += next_majority_count
    accuracy = (count_of_majority_labels / total_num_labels)
    return accuracy


# ----------------------------------------------------------
# HELPER FUNCTIONS
def setup_for_tests():
    """Creates are returns data for testing analysis methods.

    Returns: data, a list of data points
             labels, numeric labels for each data point
             centroids_dict1, three 4D centroids
             centroids_dict2, three non-random 4D centroids
                with poor starting values
    """

    list_of_points = [
            [-1.01714716,  0.95954521,  1.20493919,  0.34804443],
            [-1.36639346, -0.38664658, -1.02232584, -1.05902604],
            [1.13659605, -2.47109085, -0.83996912, -0.24579457],
            [-1.48090019, -1.47491857, -0.6221167,  1.79055006],
            [-0.31237952,  0.73762417,  0.39042814, -1.1308523],
            [-0.83095884, -1.73002213, -0.01361636, -0.32652741],
            [-0.78645408,  1.98342914,  0.31944446, -0.41656898],
            [-1.06190687,  0.34481172, -0.70359847, -0.27828666],
            [-2.01157677,  2.93965872,  0.32334723, -0.1659333],
            [-0.56669023, -0.06943413,  1.46053764,  0.01723844]
        ]
    labels = [0, 1, 0, 2, 1, 2, 1, 2, 0, 0]
    centroids_dict1 = {
            "centroid1": [0.1839742, -0.45809263, -1.91311585, -1.48341843],
            "centroid2": [-0.71767545, 1.2309971, -1.00348728, -0.38204247],
            "centroid3": [-1.71767545, 0.29971, 0.00328728, -0.38204247],
        }
    centroids_dict2 = {
            "centroid1": [0.1839742, -0.45809263, -1.91311585, -1.48341843],
            "centroid2": [10, 10, 10, 10],
            "centroid3": [-10, 1, -10, 10],
        }
    return list_of_points, labels, centroids_dict1, centroids_dict2


# ----------------------------------------------------------
# TESTS
def test_assign_update():
    # set up
    (list_of_points, labels,
     centroids_dict1, centroids_dict2) = setup_for_tests()

    # test with centroids_dict1
    answer = {'centroid3': [0, 1, 2, 1, 2, 2, 0], 'centroid1': [0],
              'centroid2': [1, 0]}

    assert_equals(answer,
                  assign_update(list_of_points, labels, centroids_dict1))

    # test with centroids_dict2
    answer = {'centroid1': [0, 1, 0, 2, 1, 2, 1, 2, 0, 0]}
    assert_equals(answer,
                  assign_update(list_of_points, labels, centroids_dict2))

    # test to make sure parameters remains unchanged
    (list_of_points_copy, labels_copy,
     centroids_dict1_copy, centroids_dict2_copy) = setup_for_tests()
    assert_equals(list_of_points_copy, list_of_points)
    assert_equals(labels_copy, labels)
    assert_equals(centroids_dict1_copy, centroids_dict1)
    assert_equals(centroids_dict2_copy, centroids_dict2)

    print("test_assign_update passed")


def test_majority_count():

    # single
    assert_equals(6, majority_count([0, 0, 0, 0, 0, 0]))
    assert_equals(5, majority_count([1, 0, 0, 0, 0, 0]))
    assert_equals(5, majority_count([0, 1, 1, 1, 1, 1]))

    # mixed
    assert_equals(4, majority_count([0, 0, 1, 1, 0, 0]))
    assert_equals(4, majority_count([0, 2, 2, 2, 3, 3, 0, 1, 1, 0, 0]))

    # tied max count
    assert_equals(4, majority_count([0, 2, 2, 2, 0, 2, 0, 0]))

    # test with labels outside of 1-10
    assert_equals(6,
                  majority_count(["cat", "cat", "cat", "cat", "cat", "cat"]))

    print("test_majority_count passed")


def test_accuracy():

    # set up
    (list_of_points, labels,
     centroids_dict1, centroids_dict2) = setup_for_tests()

    # test with centroids_dict1
    expected = 0.5
    received = accuracy(list_of_points, labels, centroids_dict1)
    assert_equals(expected, received)

    # test with centroids_dict2
    expected = 0.4
    received = accuracy(list_of_points, labels, centroids_dict2)
    assert_equals(expected, received)

    # test to make sure parameters remains unchanged
    (list_of_points_copy, labels_copy,
     centroids_dict1_copy, centroids_dict2_copy) = setup_for_tests()
    assert_equals(list_of_points_copy, list_of_points)
    assert_equals(labels_copy, labels)
    assert_equals(centroids_dict1_copy, centroids_dict1)
    assert_equals(centroids_dict2_copy, centroids_dict2)

    print("test_accuracy passed")


def main_test():

    test_assign_update()
    test_majority_count()
    test_accuracy()
    print("all tests passed.")


if __name__ == "__main__":
    centroids = load_centroids("mnist_final_centroids.csv")
    main_test()

    data, label = read_data("data/mnist.csv")
    print(accuracy(data, label, centroids))

# 1. What happened to the centroids? Why are there fewer than 10?
# Because not all centroids are necessarily assigned to a point. All
# points are assigned to a centroid but a centroid could be mapped to
# no points at all. Meaning it is no longer a key in our dictionary.

# 2. What's the accuracy of the algorithm on MNIST? By looking at the
# centroids, which digits are easier to be distinguished by the algorithm,
# and which are harder?
# The digit corresponding to centroid5 is the hardest for this algorithm to
# predict as it has the lowest accuracy. If we look at the image ourselves
# it is a little hard for a human to tell at first glance whether the number
# in the image is a 6 or a 9. This algorithm depends on key
# distinguishing features that results in two data points being mapped to
# seperate centroids
# forming various clusters.

# Total accuracy = 0.582
# centroid0 0.9523809523809523
# centroid1 0.9333333333333333
# centroid2 0.7415730337078652
# centroid3 0.8484848484848485
# centroid4 0.4666666666666667
# centroid5 0.3333333333333333
# centroid6 0.3793103448275862
# centroid7 0.7407407407407407
# centroid8 0.42857142857142855
