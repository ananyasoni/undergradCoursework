from sys import platform as sys_pf
import math


if sys_pf == 'darwin':
    import matplotlib
    matplotlib.use("TkAgg")


def check_approx_equals(expected, received):
    """Checks received against expected, and returns whether or
    not they match (True if they do, False otherwise).
    If the argument is a float, will do an approximate check.
    If the argument is a data structure will do an approximate check
    on all of its contents.

    Arguments:
        expected: the expected value
        received: the received value

    Returns: True if the received match the expected, False otherwise
    """
    try:
        if type(expected) is dict:
            # first check that keys match, then check that the
            # values approximately match
            return expected.keys() == received.keys() and \
                all([check_approx_equals(expected[k], received[k])
                    for k in expected.keys()])
        elif type(expected) is float:
            return math.isclose(expected, received, abs_tol=0.0001)
        else:
            return expected == received
    except Exception as e:
        print(f'EXCEPTION: Raised when checking check_approx_equals {e}')
        return False


def assert_equals(expected, received):
    """Checks received against expected, throws an AssertionError
    if they don't match. If the argument is a float, will do an approximate
    check. If the argument is a data structure will do an approximate check
    on all of its contents.

    Arguments:
        expected: the expected value
        received: the received value
    """
    assert check_approx_equals(expected, received), \
        f'Failed: Expected {expected}, but received {received}'
