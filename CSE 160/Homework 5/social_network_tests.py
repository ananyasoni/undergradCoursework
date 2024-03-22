import social_network as sn
from utils import assert_equals


def test_practice_graph(practice_graph):
    assert_equals(6, len(practice_graph.nodes()))
    assert_equals(8, len(practice_graph.edges()))

    # Test shape of practice graph
    assert_equals(set(["B", "C"]), set(practice_graph.neighbors("A")))
    assert_equals(set(["A", "D", "C"]), set(practice_graph.neighbors("B")))
    assert_equals(set(["A", "B", "D", "F"]),
                  set(practice_graph.neighbors("C")))
    assert_equals(set(["B", "C", "E", "F"]),
                  set(practice_graph.neighbors("D")))
    assert_equals(set(["D"]), set(practice_graph.neighbors("E")))
    assert_equals(set(["C", "D"]), set(practice_graph.neighbors("F")))
    print("practice graph tests passed")


def test_rj_graph(rj):
    assert_equals(11, len(rj.nodes()))
    assert_equals(17, len(rj.edges()))

    # Test shape of Romeo-and-Juliet graph
    assert_equals(set(["Juliet"]), set(rj.neighbors("Nurse")))
    assert_equals(set(["Juliet", "Romeo"]),
                  set(rj.neighbors("Friar Laurence")))
    assert_equals(set(["Juliet", "Capulet"]), set(rj.neighbors("Tybalt")))
    assert_equals(set(["Romeo", "Montague"]), set(rj.neighbors("Benvolio")))
    assert_equals(set(["Escalus", "Capulet", "Mercutio"]),
                  set(rj.neighbors("Paris")))
    assert_equals(set(["Paris", "Escalus", "Romeo"]),
                  set(rj.neighbors("Mercutio")))
    assert_equals(set(["Escalus", "Romeo", "Benvolio"]),
                  set(rj.neighbors("Montague")))
    assert_equals(set(["Juliet", "Tybalt", "Paris", "Escalus"]),
                  set(rj.neighbors("Capulet")))
    assert_equals(set(["Paris", "Mercutio", "Montague", "Capulet"]),
                  set(rj.neighbors("Escalus")))
    assert_equals(set(["Nurse", "Tybalt", "Capulet", "Friar Laurence",
                       "Romeo"]),
                  set(rj.neighbors("Juliet")))
    assert_equals(set(["Juliet", "Friar Laurence", "Benvolio",
                       "Montague", "Mercutio"]),
                  set(rj.neighbors("Romeo")))
    print("romeo and juliet graph tests passed")


def test_friends(rj):
    assert_equals(set(['Romeo', 'Escalus', 'Paris']),
                  sn.friends(rj, "Mercutio"))


def test_friends_of_friends(rj):
    assert_equals(set(['Benvolio', 'Capulet', 'Friar Laurence',
                       'Juliet', 'Montague']),
                  sn.friends_of_friends(rj, "Mercutio"))
    assert_equals(set(['Capulet', 'Tybalt', 'Friar Laurence',
                       'Romeo']),
                  sn.friends_of_friends(rj, "Nurse"))
    print("friends of friends test passed")


def test_common_friends(practice_graph, rj):
    assert_equals(set(['C']), sn.common_friends(practice_graph, "A", "B"))
    assert_equals(set(['B', 'C']), sn.common_friends(practice_graph, "A", "D"))
    assert_equals(set(), sn.common_friends(practice_graph, "A", "E"))
    assert_equals(set(['C']), sn.common_friends(practice_graph, "A", "F"))
    assert_equals(set(), sn.common_friends(rj, "Mercutio", "Nurse"))
    assert_equals(set(), sn.common_friends(rj, "Mercutio", "Romeo"))
    assert_equals(set(["Romeo"]), sn.common_friends(rj, "Mercutio", "Juliet"))
    assert_equals(set(["Escalus", "Paris"]),
                  sn.common_friends(rj, "Mercutio", "Capulet"))
    print("common friends tests passed")


def test_num_common_friends_map(practice_graph, rj):
    assert_equals({'D': 2, 'F': 1},
                  sn.num_common_friends_map(practice_graph, "A"))
    assert_equals({'Benvolio': 1, 'Capulet': 2, 'Friar Laurence': 1,
                   'Juliet': 1, 'Montague': 2},
                  sn.num_common_friends_map(rj, "Mercutio"))
    print("number of common friends map tests passed")


def test_num_map_to_sorted_list():
    assert_equals(['c', 'a', 'd', 'e', 'b'],
                  sn.num_map_to_sorted_list({"a": 5, "b": 2, "c": 7, "d": 5,
                                            "e": 5}))
    print("number map to sorted list test passed")


def test_recs_by_common_friends(practice_graph, rj):
    assert_equals(['D', 'F'],
                  sn.recs_by_common_friends(practice_graph,
                                            "A"))
    assert_equals(['Capulet', 'Montague', 'Benvolio',
                   'Friar Laurence', 'Juliet'],
                  sn.recs_by_common_friends(rj, "Mercutio"))
    print("recommend by number of common friends tests passed")


def test_influence_map(rj):
    assert_equals({'Benvolio': 0.2, 'Capulet': 0.5833333333333333,
                   'Friar Laurence': 0.2, 'Juliet': 0.2, 'Montague': 0.45},
                  sn.influence_map(rj, "Mercutio"))
    print("influence map test passed")


def test_recommend_by_influence(rj):
    assert_equals(['Capulet', 'Montague', 'Benvolio',
                   'Friar Laurence', 'Juliet'],
                  sn.recommend_by_influence(rj, "Mercutio"))
    print("recommend by influence test passed")


def main_test():
    practice_graph = sn.get_practice_graph()
    rj = sn.get_romeo_and_juliet_graph()
    test_practice_graph(practice_graph)
    test_rj_graph(rj)
    test_friends(rj)
    test_friends_of_friends(rj)
    test_common_friends(practice_graph, rj)
    test_num_common_friends_map(practice_graph, rj)
    test_num_map_to_sorted_list()
    test_recs_by_common_friends(practice_graph, rj)
    test_influence_map(rj)
    test_recommend_by_influence(rj)
    print("all tests passed")


if __name__ == "__main__":
    main_test()
