# Name: Ananya Shreya Soni
# CSE 160
# Homework 5

import utils  # noqa: F401, do not remove if using a Mac
import networkx as nx
import matplotlib.pyplot as plt
from operator import itemgetter

def get_practice_graph():
    """Builds and returns the practice graph
    """
    practice_graph = nx.Graph()
    practice_graph.add_edge("A", "B")
    practice_graph.add_edge("A", "C")
    practice_graph.add_edge("B", "C")
    practice_graph.add_edge("B", "D")
    practice_graph.add_edge("D", "F")
    practice_graph.add_edge("D", "C")
    practice_graph.add_edge("C", "F")
    practice_graph.add_edge("D", "E")
    return practice_graph


def draw_practice_graph(graph):
    """Draw practice_graph to the screen.
    """
    nx.draw_networkx(graph)
    plt.show()

def get_romeo_and_juliet_graph():
    """Builds and returns the romeo and juliet graph
    """
    rj = nx.Graph()
    rj.add_edge("Nurse", "Juliet")
    rj.add_edge("Juliet", "Tybalt")
    rj.add_edge("Juliet", "Capulet")
    rj.add_edge("Juliet", "Romeo")
    rj.add_edge("Juliet", "Friar Laurence")
    rj.add_edge("Romeo", "Friar Laurence")
    rj.add_edge("Romeo", "Benvolio")
    rj.add_edge("Romeo", "Montague")
    rj.add_edge("Romeo", "Mercutio")
    rj.add_edge("Capulet", "Paris")
    rj.add_edge("Capulet", "Escalus")
    rj.add_edge("Capulet", "Tybalt")
    rj.add_edge("Benvolio", "Montague")
    rj.add_edge("Montague", "Escalus")
    rj.add_edge("Escalus", "Mercutio")
    rj.add_edge("Escalus", "Paris")
    rj.add_edge("Paris", "Mercutio")
    return rj


def draw_rj(graph):
    """Draws the rj graph to the screen and to a file.
    """
    nx.draw_networkx(graph)
    plt.savefig("romeo-and-juliet.pdf")
    plt.show()


def friends(graph, user):
    """Returns a set of the friends of the given user, in the given graph.
    """
    return set(graph.neighbors(user))


def friends_of_friends(graph, user):
    """Find and return the friends of friends of the given user.

    Arguments:
        graph: the graph object that contains the user and others
        user: unique identifier

    Returns: a set containing the names of all of the friends of
    friends of the user. The set should not contain the user itself
    or their immediate friends.
    """
    # create new set to return
    set_of_friends_of_friends = set()
    # get the set of friends of the given user
    set_of_friends = friends(graph, user)
    # itterate through that set of friends and call the friends method
    # to retrieve the set of friends of each friend of the user
    # and add it to the set that this method returns
    for friend in set_of_friends:
        friends_of_friend = friends(graph, friend)
        for next_friend in friends_of_friend:
            # make sure whatever friend you add isn't the user themselves
            # or already a friend of the user
            if (next_friend not in set_of_friends and next_friend != user):
                set_of_friends_of_friends.add(next_friend)
    return set_of_friends_of_friends


def common_friends(graph, user1, user2):
    """
    Finds and returns the set of friends that user1
    and user2 have in common.

    Arguments:
        graph:  the graph object that contains the users
        user1: a unique identifier representing one user
        user2: a unique identifier representing another user

    Returns: a set containing the friends user1 and user2 have in common
    """
    user1_friends = friends(graph, user1)
    user2_friends = friends(graph, user2)
    return user1_friends.intersection(user2_friends)


def num_common_friends_map(graph, user):
    """Returns a map (a dictionary), mapping a person to the number of friends
    that person has in common with the given user. The map keys are the
    people who have at least one friend in common with the given user,
    and are neither the given user nor one of the given user's friends.
    Example: a graph called my_graph and user "X"
    Here is what is relevant about my_graph:
        - "X" and "Y" have two friends in common
        - "X" and "Z" have one friend in common
        - "X" and "W" have one friend in common
        - "X" and "V" have no friends in common
        - "X" is friends with "W" (but not with "Y" or "Z")
    Here is what should be returned:
      num_common_friends_map(my_graph, "X")  =>   { 'Y':2, 'Z':1 }

    Arguments:
        graph: the graph object that contains the user and others
        user: unique identifier

    Returns: a dictionary mapping each person to the number of (non-zero)
    friends they have in common with the user
    """
    # create dictionary to return
    num_common_friends = {}
    # retrieve the set of friends_of_friends of the user
    set_of_friends_of_friends = friends_of_friends(graph, user)
    # keep a counter to track the number of friends in common with the friends
    # of the user
    num_friends_common = 0
    # iterate through the set_of_friends_of_friends of the user
    for next_friend in set_of_friends_of_friends:
        # find the number of common friends between the next friend
        # in the users set of friends of friends and the user
        num_friends_common = len(common_friends(graph, user, next_friend))
        # if count is at least one add friend as key to map and value as count
        if num_friends_common >= 1:
            num_common_friends[next_friend] = num_friends_common
        # reset the count to 0 --> num_friends_common = 0
        num_friends_common = 0
    return num_common_friends


def num_map_to_sorted_list(map_with_number_vals):
    """Given a dictionary, return a list of the keys in the dictionary.
    The keys are sorted by the number value they map to, from greatest
    number down to smallest number.
    When two keys map to the same number value, the keys are sorted by their
    natural sort order for whatever type the key is, from least to greatest.

    Arguments:
        map_with_number_vals: a dictionary whose values are numbers

    Returns: a list of keys, sorted by the values in map_with_number_vals
    """
    key_and_values = map_with_number_vals.items()
    # sort alphabetically
    key_and_values = sorted(key_and_values, key=itemgetter(0))
    # sort numerically
    key_and_values = sorted(key_and_values, key=itemgetter(1), reverse=True)
    list_of_sorted_keys = []
    for next_key_value_pair in key_and_values:
        list_of_sorted_keys.append(next_key_value_pair[0])
    return list_of_sorted_keys


def recs_by_common_friends(graph, user):
    """
    Returns a list of friend recommendations for the user, sorted
    by number of friends in common.

    Arguments:
        graph: the graph object that contains the user and others
        user: a unique identifier

    Returns: A list of friend recommendations for the given user.
    The friend recommendation list consists of names/IDs of people in
    the graph who are not yet a friend of the given user.  The order
    of the list is determined by the number of common friends (people
    with the most common friends are listed first).  In the
    case of a tie in number of common friends, the names/IDs are
    sorted by their natural sort order, from least to greatest.
    """
    common_friends_map = num_common_friends_map(graph, user)
    return num_map_to_sorted_list(common_friends_map)


def influence_map(graph, user):
    """Returns a map (a dictionary) mapping from each person to their
    influence score, with respect to the given user. The map only
    contains people who have at least one friend in common with the given
    user and are neither the user nor one of the users's friends.
    See the assignment writeup for the definition of influence scores.
    """
    influence_map = {}
    # retrieve my list of friends of friends
    set_of_friends_of_friends = friends_of_friends(graph, user)
    # for each friend find the list of mutual friends they have with me
    for friend in set_of_friends_of_friends:
        influence_score = 0
        set_of_mutuals = common_friends(graph, user, friend)
        # for each of those mutual find the total number of friends they have
        for mutual in set_of_mutuals:
            total_num_f = len(friends(graph, mutual))
            # compute the friend's influence score
            influence_score += (1 / total_num_f)
        influence_map[friend] = influence_score
        influence_score = 0
    return influence_map


def recommend_by_influence(graph, user):
    """Return a list of friend recommendations for the given user.
    The friend recommendation list consists of names/IDs of people in
    the graph who are not yet a friend of the given user.  The order
    of the list is determined by the influence score (people
    with the biggest influence score are listed first).  In the
    case of a tie in influence score, the names/IDs are sorted
    by their natural sort order, from least to greatest.
    """
    # retrieve influence map
    influence_dict = influence_map(graph, user)
    # sort
    return num_map_to_sorted_list(influence_dict)


def get_facebook_graph(filename):
    """Builds and returns the facebook graph
    Arguments:
        filename: the name of the datafile
    """
    facebook_connections_file = open(filename, "r")
    facebook_graph = nx.Graph()
    # for each line in the file the first two sequencences
    # in each line represent two users where user1 has
    # user2 in their friends list and user2 has user1 in their
    # friends list --> user1 and user2 connected by an edge
    # ex: 35467    17494    1197992662
    for next_line in facebook_connections_file:
        seperated_line = next_line.split()
        user1 = int(seperated_line[0])
        user2 = int(seperated_line[1])
        facebook_graph.add_edge(user1, user2)
    facebook_connections_file.close()
    return facebook_graph


# def test_get_facebook_graph(facebook, filename):
#     if (filename == "facebook-links-small.txt"):
#         pass
#         # assert len(facebook.edges()) == 1494
#     else:
#         assert len(facebook.nodes()) == 63731
#         assert len(facebook.edges()) == 817090
#         # print("passed facebook graph test!")


def main():
    # practice_graph = get_practice_graph()
    # draw_practice_graph(practice_graph)
    rj = get_romeo_and_juliet_graph()
    draw_rj(rj)
    unchanged_list = []
    changed_list = []
    for character in rj.nodes():
        character_first_rec = recs_by_common_friends(rj, character)
        character_sec_rec = recommend_by_influence(rj, character)
        # both approaches give the same recommendation
        if (character_first_rec == character_sec_rec):
            unchanged_list.append(character)
        # both approaches do not give the same recommendation
        else:
            changed_list.append(character)

    print("Problem 4:")
    print()
    print("Unchanged Recommendations:", sorted(unchanged_list))
    print("Changed Recommendations:", sorted(changed_list))
    # fb_filename = "facebook-links.txt"
    fb_filename = "facebook-links-small.txt"
    facebook = get_facebook_graph(fb_filename)
    # call get_facebook_graph:
    # test_get_facebook_graph(facebook, fb_filename)

    
    print()
    print("Problem 6:")
    print()

    # create a list of valid users in the facebook graph
    # a valid user id is one that is a multiple of 1000
    facebook_users = list(facebook.nodes())
    users_with_multiple_1000_id = []
    for user in facebook_users:
        if user % 1000 == 0:
            users_with_multiple_1000_id.append(user)
    # sort the valid users in ascending order
    users_with_multiple_1000_id.sort()
    # for each user print a list containing the first 10 friend
    # recommendations by the number of common friends algorithm
    # keep track of the users whose friend recommendations that are
    # the same under both recommendation systems and different under
    # both recommendation systems
    unchanged_list = []
    changed_list = []
    for user in users_with_multiple_1000_id:
        first_rec = recs_by_common_friends(facebook, user)
        sec_rec = recommend_by_influence(facebook, user)
        # print the top 10 recommendations by number of common friends
        print(str(user) + " (by num_common_friends): " + str(first_rec[0:10]))
        # both approaches give the same recommendation
        if (first_rec == sec_rec):
            unchanged_list.append(user)
        # both approaches do not give the same recommendation
        else:
            changed_list.append(user)


    print()
    print("Problem 7:")
    print()
    for next_user in users_with_multiple_1000_id:
        recs = recommend_by_influence(facebook, next_user)
        # print the top 10 recommendations by influence
        print(str(next_user) + " (by influence): " + str(recs[0:10]))


    print()
    print("Problem 8:")
    print()
    print("Same: " + str(len(unchanged_list)))
    print("Different: " + str(len(changed_list)))


if __name__ == "__main__":
    main()
