package com.edu.egg.virtual_wallet.utility;

import java.math.BigInteger;
import java.util.UUID;

public class Utilities {

    public static Long generateAccountNumber() {
        return UUID.randomUUID().getMostSignificantBits() & Integer.MAX_VALUE;
    }

    public static String generateAccountCvu() {
        String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        return lUUID.substring(0, 22);
    }

    public static String generateAlias() {
        String genericAlias;
        do {

            String[] namesListOne = {"the", "of", "and", "a", "to", "in", "is", "you", "that", "it", "he", "was", "for", "on", "are", "as", "with", "his", "they", "I", "at", "be", "this", "have", "from", "or", "one", "had", "by", "word", "but", "not", "what", "all", "were", "we", "when", "your", "can", "said", "there", "use", "an", "each", "which", "she", "do", "how", "their", "if", "will", "up", "other", "about", "out", "many", "then", "them", "these", "so", "some", "her", "would", "make", "like", "him", "into", "time", "has", "look", "two", "more", "write", "go", "see", "number", "no", "way", "could", "people", "my", "than", "first", "water", "been", "call", "who", "oil", "its", "now", "find", "long", "down", "day", "did", "get", "come", "made", "may", "part","java", "python"};

            String[] namesListTwo = {"service", "chapter", "noon", "copper", "list", "arrow", "him", "aloud", "job", "pass", "tall", "skill", "deep", "explain", "combine", "told", "race", "made", "four", "sum", "lucky", "fast", "except", "care", "one", "day", "hole", "until", "met", "me", "dog", "teacher", "lovely", "rear", "battle", "mix", "again", "per", "live", "melted", "head", "shadow", "main", "western", "headed", "instant", "age", "monday", "least", "species", "mix", "sight", "web", "ahead", "growth", "aboard", "plates", "hair", "bar", "student", "silver", "share", "nodded", "cannot", "better", "car", "light", "pencil", "cabin", "tool", "cage", "blue", "town", "gulf", "small", "sight", "special", "trick", "element", "donkey", "swung", "lesson", "red", "rice", "fewer", "its", "sure", "gave", "truth", "fruit", "up"};

            String[] namesListThree = {"tongue", "search", "ill", "sleep", "gap", "dad", "shake", "feet", "life", "mouse", "mail", "idea", "rising", "oxygen", "forth", "two", "rail", "dug", "cat", "love", "bird", "call", "dollar", "slabs", "mark", "want", "pay", "farm", "strong", "knife", "jungle", "might", "thumb", "gain", "art", "tears", "place", "pack", "climate", "obtain", "look", "black", "lead", "travel", "slight", "threw", "castle", "strip", "ranch", "mirror", "glass", "act", "hat", "home", "charge", "model", "ear", "north", "ant", "swam", "touch", "eaten", "word", "heart", "fed", "receive", "wet", "equal", "age", "men", "grass", "eat", "prepare", "skill", "morning", "dawn", "sun", "dig", "voice", "rap", "solve", "combine", "ever", "bed", "private", "phone", "road", "today", "ruby", "scala" };

            genericAlias = namesListOne[(int) (Math.random() * namesListOne.length)] + "." + namesListTwo[(int) (Math.random() * namesListTwo.length)] + "." + namesListThree[(int) (Math.random() * namesListThree.length)];

        } while (genericAlias.length() > 20);

        return genericAlias.toUpperCase();
    }


}
