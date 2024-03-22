import fraud_detection as fd
import math


def test_ones_and_tens_digit_histogram():
    # Easy to calculate case: 5 numbers, clean percentages.
    actual = fd.ones_and_tens_digit_histogram([127, 426, 28, 9, 90])
    expected = [0.2, 0.0, 0.3, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.2]
    for i in range(len(actual)):
        assert math.isclose(actual[i], expected[i])

    # Obscure and hard (by hand) to calculate frequencies
    input = [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89,
             144, 233, 377, 610, 987, 1597, 2584, 4181, 6765]
    actual = fd.ones_and_tens_digit_histogram(input)
    expected = [0.21428571428571427, 0.14285714285714285, 0.047619047619047616,
                0.11904761904761904, 0.09523809523809523, 0.09523809523809523,
                0.023809523809523808, 0.09523809523809523, 0.11904761904761904,
                0.047619047619047616]
    for i in range(len(actual)):
        assert math.isclose(actual[i], expected[i])


def test_extract_election_votes():
    # spec example test that the length of the list returned is 120
    # for the 4 candidates Ahmadinejad, Rezai,
    # Karrubi, and Mousavi
    actual = fd.extract_election_votes("election-iran-2009.csv",
                                       ["Ahmadinejad", "Rezai", "Karrubi",
                                        "Mousavi"])
    assert len(actual) == 120

    # empty test cases -> returned list should be empty
    # when passed no column names or invalid column names
    actual = fd.extract_election_votes("election-iran-2009.csv",
                                       [])  # no column names
    assert len(actual) == 0
    actual = fd.extract_election_votes("election-iran-2009.csv",
                                       ["invalid1", "invalid2"])
    # invalid column names
    assert len(actual) == 0

    # tests that for each candidate by themselves the list returned has a size
    # of 30
    actual = fd.extract_election_votes("election-iran-2009.csv",
                                       ["Ahmadinejad"])
    assert len(actual) == 30
    actual = fd.extract_election_votes("election-iran-2009.csv", ["Rezai"])
    assert len(actual) == 30
    actual = fd.extract_election_votes("election-iran-2009.csv", ["Karrubi"])
    assert len(actual) == 30
    actual = fd.extract_election_votes("election-iran-2009.csv", ["Mousavi"])
    assert len(actual) == 30
    # print("passed")


def test_mean_squared_error():
    # spec example test
    actual = fd.mean_squared_error([1, 4, 9], [6, 5, 4])
    assert math.isclose(actual, 17.0, rel_tol=1e-03)

    # edge case: test for 0 mean squared error
    # with various different inputs
    # lists of just 0s
    actual = fd.mean_squared_error([0, 0, 0], [0, 0, 0])
    assert math.isclose(actual, 0.0, rel_tol=1e-03)
    actual = fd.mean_squared_error([0], [0])
    assert math.isclose(actual, 0.0, rel_tol=1e-03)
    # testing positive and negative numbers
    actual = fd.mean_squared_error([-1, 0, 1, 2, 3], [-1, 0, 1, 2, 3])
    assert math.isclose(actual, 0.0, rel_tol=1e-03)
    actual = fd.mean_squared_error([-20, 34, 0], [-20, 34, 0])
    assert math.isclose(actual, 0.0, rel_tol=1e-03)
    # testing floats
    actual = fd.mean_squared_error([0.1, 0.2, 0.3, 0.4, 0.5],
                                   [0.1, 0.2, 0.3, 0.4, 0.5])
    assert math.isclose(actual, 0.0, rel_tol=1e-03)
    # testing large integers
    actual = fd.mean_squared_error([10000, 20000, 30000, 40000, 50000],
                                   [10000, 20000, 30000, 40000, 50000])
    assert math.isclose(actual, 0.0, rel_tol=1e-03)
    # print("passed")

    # complex examples --> hard to compute by hand
    actual = fd.mean_squared_error([34, 37, 44, 47, 48, 48, 46, 43, 32, 27,
                                    26, 24],
                                   [37, 40, 46, 44, 46, 50, 45, 44, 34, 30,
                                    22, 23])
    assert math.isclose(actual, 5.916, rel_tol=1e-03)
    actual = fd.mean_squared_error([4.21, 8.95, 2.34, 15.67, 7.89, 12.45,
                                    3.67, 9.01, 6.78, 11.23, 14.56, 1.12,
                                    5.67, 10.34, 13.78, 16.45, 20.89, 18.76,
                                    22.01, 25.34, 27.89, 30.12, 24.56, 28.01,
                                    32.45, 35.67, 31.23, 36.78, 40.21, 38.95],
                                   [2.34, 7.89, 11.23, 15.67, 20.89, 24.56,
                                    28.01, 32.45, 36.78, 40.21, 8.95, 3.67,
                                    9.01, 6.78, 12.45, 16.45, 18.76, 22.01,
                                    25.34, 27.89, 5.67, 10.34, 13.78, 1.12,
                                    14.56, 30.12, 35.67, 31.23, 38.95, 4.21])
    assert math.isclose(actual, 223.712, rel_tol=1e-03)


def test_calculate_mse_with_uniform():
    # spec example
    histogram = fd.ones_and_tens_digit_histogram(fd.extract_election_votes(
        "election-iran-2009.csv", ["Ahmadinejad",
                                   "Rezai", "Karrubi", "Mousavi"]))
    actual = fd.calculate_mse_with_uniform(histogram)
    assert math.isclose(actual, 0.000739583333333, rel_tol=1e-03)
    histogram = fd.ones_and_tens_digit_histogram(fd.extract_election_votes(
        "election-iran-2009.csv", ["Ahmadinejad"]))
    actual = fd.calculate_mse_with_uniform(histogram)
    assert math.isclose(actual, 0.0010, rel_tol=1e-03)

    # complex examples --> hard to compute by hand
    actual = fd.calculate_mse_with_uniform([0.065, 0.018, 0.14, 0.023, 0.162,
                                            0.051, 0.312, 0.012, 0.123, 0.094])
    assert math.isclose(actual, 0.0075, rel_tol=1e-03)
    actual = fd.calculate_mse_with_uniform([0.032, 0.123, 0.045, 0.189, 0.021,
                                            0.256, 0.078, 0.143, 0.045, 0.068])
    assert math.isclose(actual, 0.00531, rel_tol=1e-03)

    # zero case (when passed uniform distribution should return 0)
    actual = fd.calculate_mse_with_uniform([0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1,
                                            0.1, 0.1, 0.1])
    assert math.isclose(actual, 0.0, rel_tol=1e-03)


def main():
    test_ones_and_tens_digit_histogram()
    test_extract_election_votes()
    test_mean_squared_error()
    test_calculate_mse_with_uniform()


if __name__ == "__main__":
    main()
