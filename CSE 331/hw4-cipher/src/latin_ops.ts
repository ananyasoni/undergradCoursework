import { explode, compact } from './char_list';
import { List, nil, cons, concat, len, rev } from './list';
import { prefix, suffix} from './list_ops';

/** Determines whether the given character is a vowel. */
const is_latin_vowel = (c: number): boolean => {
    const ch = String.fromCharCode(c).toLowerCase();
    return "aeiouy".indexOf(ch) >= 0;
};

/** Determines whether the given character is a Latin consonant. */
const is_latin_consonant = (c: number): boolean => {
    const ch = String.fromCharCode(c).toLowerCase();
    return "bcdfghjklmnpqrstvwxz".indexOf(ch) >= 0;
};

/** Changes most Latin alphabetic characters to different ones. */
export const next_latin_char = (c: number): number => {
    switch (String.fromCharCode(c)) {
        case "a": return "i".charCodeAt(0);
        case "e": return "y".charCodeAt(0);
        case "i": return "u".charCodeAt(0);
        case "o": return "a".charCodeAt(0);
        case "u": return "o".charCodeAt(0);
        case "y": return "e".charCodeAt(0);

        case "b": return "t".charCodeAt(0);
        case "p": return "g".charCodeAt(0);
        case "j": return "d".charCodeAt(0);
        case "g": return "j".charCodeAt(0);
        case "d": return "b".charCodeAt(0);
        case "t": return "p".charCodeAt(0);

        case "c": return "z".charCodeAt(0);
        case "k": return "c".charCodeAt(0);
        case "s": return "k".charCodeAt(0);
        case "z": return "s".charCodeAt(0);

        case "f": return "w".charCodeAt(0);
        case "v": return "f".charCodeAt(0);
        case "w": return "v".charCodeAt(0);

        case "h": return "r".charCodeAt(0);
        case "l": return "h".charCodeAt(0);
        case "r": return "l".charCodeAt(0);

        case "m": return "n".charCodeAt(0);
        case "n": return "m".charCodeAt(0);

        case "q": return "x".charCodeAt(0);
        case "x": return "q".charCodeAt(0);

        default: return c;
    }
};

/** Inverse of next_char. */
export const prev_latin_char = (c: number): number => {
    switch (String.fromCharCode(c)) {
        case "a": return "o".charCodeAt(0);
        case "e": return "y".charCodeAt(0);
        case "i": return "a".charCodeAt(0);
        case "o": return "u".charCodeAt(0);
        case "u": return "i".charCodeAt(0);
        case "y": return "e".charCodeAt(0);

        case "b": return "d".charCodeAt(0);
        case "p": return "t".charCodeAt(0);
        case "j": return "g".charCodeAt(0);
        case "g": return "p".charCodeAt(0);
        case "d": return "j".charCodeAt(0);
        case "t": return "b".charCodeAt(0);

        case "c": return "k".charCodeAt(0);
        case "k": return "s".charCodeAt(0);
        case "s": return "z".charCodeAt(0);
        case "z": return "c".charCodeAt(0);

        case "f": return "v".charCodeAt(0);
        case "v": return "w".charCodeAt(0);
        case "w": return "f".charCodeAt(0);

        case "h": return "l".charCodeAt(0);
        case "l": return "r".charCodeAt(0);
        case "r": return "h".charCodeAt(0);

        case "m": return "n".charCodeAt(0);
        case "n": return "m".charCodeAt(0);

        case "q": return "x".charCodeAt(0);
        case "x": return "q".charCodeAt(0);

        default: return c;
    }
};

/**
 * Returns the number of consonants at the start of the given string
 * before the first vowel, or -1 if there are no vowels
 */
export const count_consonants = (L: List<number>): bigint => {
    if (L.kind === "nil") {
        return -1n;
    } else if (is_latin_vowel(L.hd)) {
        return 0n;
    } else if (is_latin_consonant(L.hd)) {
        const n = count_consonants(L.tl);
        if (n === -1n) {
            return -1n;
        } else {
            return n + 1n;
        }
    } else {
        // not a vowel or a consonant
        return -1n;
    }
};

/**
 * Takes a list of characters as an argument and returns a list of the same length
 * but with each character replaced by the ‘next’ Latin character
 */
export const cipher_encode = (L: List<number>): List<number> => {
    if (L.kind === "nil") {
        return nil;
    } else {
        return cons(next_latin_char(L.hd), cipher_encode(L.tl));
    }
};

/**
 * Takes a list of characters and returns a list of the same length but with each character
 * replaced by the ‘previous’ Latin character
 */
export const cipher_decode = (L: List<number>): List<number> => {
    if (L.kind === "nil") {
        return nil;
    } else {
        return cons(prev_latin_char(L.hd), cipher_decode(L.tl));
    }
};

/**
 * This function (crazy-caps-encode), takes a list of characters as an argument and returns a list of the same
 * length but with every other character, starting with the second, made upper case
 */
export const crazy_caps_encode = (L: List<number>): List<number> => {
    if (L.kind === "nil") {
        return nil;
    } else if (L.tl.kind === "nil") {
        return cons(L.hd, nil);
    } else {
        return cons(L.hd, cons(String.fromCharCode(L.tl.hd).toUpperCase().charCodeAt(0), crazy_caps_encode(L.tl.tl)));
    }
};

/**
 * This function (crazy-caps-decode), takes a list of characters as an argument and returns a list of the
 * same length but with every other character, starting with the second, made lower case. This undoes the effect
 * of crazy-caps-encode.
 */
export const crazy_caps_decode = (L: List<number>): List<number> => {
    if (L.kind === "nil") {
        return nil;
    } else if (L.tl.kind === "nil") {
        return cons(L.hd, nil);
    } else {
        return cons(L.hd, cons(String.fromCharCode(L.tl.hd).toLowerCase().charCodeAt(0), crazy_caps_decode(L.tl.tl)));
    }
};

/**
 * This function (frog_latin_encode) takes a list of characters as input and returns a
 * new list of characters, translating a word into Frog Latin
 * If the list does not contain a vowel (i.e. all consonants or no letters), return the input unchanged.
 * If the list begins with n = 0 consonants followed by a vowel (i.e. begins with a vowel), return “f”
 * appended to the front of the list representing the original string followed by “rog”, e.g., “elf” becomes
 * “felfrog”. If the list begins with n ≥ 1 consonants followed by a vowel (i.e. begins with at least 1 consonant,
 * then a vowel), return a list with the suffix of the original list starting at that vowel, followed by
 * the prefix of the list containing only the initial consonants, followed by “rog”, e.g., “thing” becomes
 * “ingthrog”, “stray” becomes “aystrrog”, and “Kevin” becomes “evinkrog”.
 */
export const frog_latin_encode = (L: List<number>): List<number> => {
    const numOfCBeforeV : bigint = count_consonants(L)
    //case 1 (all constants aka no vowels)
    if(L.kind === "nil" || numOfCBeforeV === -1n) {
        return L;
    //case 2 (n = 0 constants at the start aka starts with vowel)
    } else if (numOfCBeforeV === 0n) {
        return concat(explode("f"), concat(L, explode("rog")));
    //case 3 (n >= 0 constants at the start aka starts with at least one consonant folowed by a vowel)
    } else {
        return concat(suffix(numOfCBeforeV, L), concat(prefix(numOfCBeforeV, L), explode("rog")));
    }
};

/**
 * If the list ends in “rog” and the list’s initial character is “f” followed immediately by a vowel return
 * everything following “f” and before “rog”, e.g., “forangerog” becomes “orange” and “featrog” becomes “eat”. (undoing case 2 in encode)
 * If the list ends in “rog” with at least one consonant immediately before, and the list starts with a
 * vowel, return those consonants followed by the characters before the consonants. e.g., “ameshrog” becomes “shame”,
 * and “entrog” becomes “nte”. (undoing case 3) Otherwise, if the list does not end in “rog”, or the initial character
 * is not “f” followed immediately by a vowel and the list does not start with a vowel and have consonants just before "rog",
 * return it as is. (It doesn’t look like Frog Latin.) e.g. “aterog” and “strog”. (undoing case 1)
 */
export const frog_latin_decode = (L: List<number>): List<number> => {
    const lenOfListGOrEq5AndListEndsWithRog : boolean = (len(L) >= 5) ? compact(suffix(len(L) - 3n, L)) === "rog" : false;
    const allButRogAtEnd : List<number> = (lenOfListGOrEq5AndListEndsWithRog) ? prefix(len(L) - 3n, L) : nil;
    // case 1
    // list ends in “rog” and the list’s initial character is “f” followed immediately by a vowel
    if(lenOfListGOrEq5AndListEndsWithRog && compact(prefix(1n, L)) === "f" && count_consonants(prefix(2n, L)) === 1n) {
        // return everything between f and rog
        return suffix(1n, allButRogAtEnd);
    //case 2
    // list ends in “rog” with at least one consonant immediately before, and the list starts with a vowel
    } else if (lenOfListGOrEq5AndListEndsWithRog && count_consonants(suffix(3n, rev(L))) >= 1 && count_consonants(L) === 0n) {
        const index : bigint = len(L) - 3n - count_consonants(rev(allButRogAtEnd))
        //return the consonants right before "rog" followed by the characters before the consonants
        return concat(suffix(index, allButRogAtEnd), prefix(index, L));
    //case 3
    // does not fall into case 1 or case 2 aka the list does not end in “rog”, or the initial character is not “f” followed
    // immediately by a vowel and the list does not start with a vowel and have consonants
    } else {
        // return input unchanged
        return L;
    }
};

