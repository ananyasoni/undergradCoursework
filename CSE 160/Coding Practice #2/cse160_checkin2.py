# Ananya Shreya Soni
# CSE 160
# Autumn 2023
# Checkin 2


# Problem 1
def squirrel_play(temp, is_summer):
    '''
    The squirrels in Palo Alto spend most of the day playing. In particular,
    they play if the temperature is between 60 and 90 (inclusive).
    Unless it is summer, then the upper limit is 100 instead of 90.
    Given an int temperature and a boolean is_summer,
    return True if the squirrels play and False otherwise.

    Arguments:
        temp: an integer
        is_summer: a boolean

    Returns: a boolean representing whether or not the squirrels will play
    '''
    if is_summer:
        if temp >= 60 and temp <= 100:
            return True
        return False
    else:
        if temp >= 60 and temp <= 90:
            return True
        return False


assert squirrel_play(70, False) is True
assert squirrel_play(95, False) is False
assert squirrel_play(95, True) is True
assert squirrel_play(90, False) is True
assert squirrel_play(90, True) is True
assert squirrel_play(50, False) is False
assert squirrel_play(50, True) is False
assert squirrel_play(100, False) is False
assert squirrel_play(100, True) is True


# Problem 2
def caught_speeding(speed, is_birthday):
    '''
    You are driving a little too fast, and a police officer stops you.
    Write code to compute the result, encoded as an int value:
    0=no ticket, 1=small ticket, 2=big ticket.
    If speed is 60 or less, the result is 0.
    If speed is between 61 and 80 inclusive, the result is 1.
    If speed is 81 or more, the result is 2.
    Unless it is your birthday -- on that day,
    your speed can be 5 higher in all cases.

    Arguments:
        speed: an integer
        is_birthday: a boolean

    Returns: An integer representing the value of the ticket recieved
    '''
    if is_birthday:
        if speed <= 65:
            return 0
        elif speed >= 66 and speed <= 86:
            return 1
        else:
            return 2
    else:
        if speed <= 60:
            return 0
        elif speed >= 61 and speed <= 81:
            return 1
        else:
            return 2


assert caught_speeding(60, False) == 0
assert caught_speeding(65, False) == 1
assert caught_speeding(65, True) == 0
assert caught_speeding(80, False) == 1
assert caught_speeding(85, False) == 2
assert caught_speeding(85, True) == 1
assert caught_speeding(70, False) == 1
assert caught_speeding(75, False) == 1


# Problem 3
def alarm_clock(day, vacation):
    '''
    Given a day of the week encoded as 0=Sun, 1=Mon, 2=Tue, ...6=Sat,
    and a boolean indicating if we are on vacation, return a string of the
    form "7:00" indicating when the alarm clock should ring.
    Weekdays, the alarm should be "7:00"
    and on the weekend it should be "10:00".
    Unless we are on vacation -- then on weekdays it should be "10:00"
    and weekends it should be "off".

    Arguments:
        day: an integer representing a day
        vacation: a boolean representing if you are on vacation

    Returns: A string representing the time when we should wake up
    '''
    # your solution code should start here
    if vacation:
        for i in [1, 2, 3, 4, 5]:
            if day == i:
                return "10:00"
        return "off"
    else:
        for i in [1, 2, 3, 4, 5]:
            if day == i:
                return "7:00"
        return "10:00"


assert alarm_clock(1, False) == '7:00'
assert alarm_clock(5, False) == '7:00'
assert alarm_clock(0, False) == '10:00'
assert alarm_clock(6, False) == '10:00'
assert alarm_clock(0, True) == 'off'
assert alarm_clock(6, True) == 'off'
assert alarm_clock(1, True) == '10:00'
assert alarm_clock(3, True) == '10:00'
