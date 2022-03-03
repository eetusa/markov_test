package fi.eetu;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.text.DecimalFormat;


public class Main {
    private static final DecimalFormat df = new DecimalFormat("0.000");
    public static void main(String[] args) {
        int total_vowels = 0;
        int total_consonants = 0;
        int total_others = 0;
        int vowel_to_vowel = 0;
        int vowel_to_consonant = 0;
        int vowel_to_other = 0;
        int consonant_to_consonant = 0;
        int consonant_to_vowel = 0;
        int consonant_to_other = 0;
        int other_to_other = 0;
        int other_to_consonant = 0;
        int other_to_vowel = 0;

        int total_chars = 0;

        boolean previousWasVowel = false;
        boolean previousWasConsonant = false;
        boolean previousWasOther = false;
        String vowels = "aeiouyäöåü";
        String consonants = "bcdfghjklmnpqrstvwxzš";

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().toLowerCase();
                for (int i = 0; i < data.length(); i++){
                    char current_char =data.charAt(i);
                    int indexInVowels = vowels.indexOf(current_char);
                    int indexInConsonants = consonants.indexOf(current_char);
                    total_chars++;

                    if  (indexInVowels != -1){  // if vowel
                        total_vowels++;
                        if (previousWasConsonant){
                            consonant_to_vowel++;
                        } else if (previousWasVowel){
                            vowel_to_vowel++;
                        } else if (previousWasOther){
                            other_to_vowel++;
                        }
                        previousWasOther = false;
                        previousWasConsonant = false;
                        previousWasVowel = true;
                    } else if (indexInConsonants != -1){ // if consonant
                        total_consonants++;
                        if (previousWasConsonant){
                            consonant_to_consonant++;
                        } else if (previousWasVowel){
                            vowel_to_consonant++;
                        } else if (previousWasOther){
                            other_to_consonant++;
                        }
                        previousWasOther = false;
                        previousWasVowel = false;
                        previousWasConsonant = true;
                    } else {    // if other
                        total_others++;
                        if (previousWasConsonant){
                            consonant_to_other++;
                        } else if (previousWasVowel){
                            vowel_to_other++;
                        } else if (previousWasOther){
                            other_to_other++;
                        }
                        previousWasOther = true;
                        previousWasVowel = false;
                        previousWasConsonant = false;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        if (total_chars > 0){
            System.out.println("\t\t\tVokaali\t\tKonsonantti\t\tMuu");
            System.out.println("Vokaali \t| " + df.format((double)vowel_to_vowel/(vowel_to_consonant+vowel_to_vowel+vowel_to_other)) + "\t\t"+df.format((double)vowel_to_consonant/(vowel_to_consonant+vowel_to_vowel+vowel_to_other))+"\t\t" + df.format((double)vowel_to_other/(vowel_to_consonant+vowel_to_vowel+vowel_to_other)) +"\t|");
            System.out.println("Konsonantti | " + df.format((double)consonant_to_vowel/(consonant_to_consonant+consonant_to_other+consonant_to_vowel)) + "\t\t" + df.format((double)consonant_to_consonant/(consonant_to_consonant+consonant_to_other+consonant_to_vowel)) +"\t\t" + df.format((double)consonant_to_other/(consonant_to_consonant+consonant_to_other+consonant_to_vowel)) + "\t|");
            System.out.println("Muu\t\t\t| " + df.format((double)other_to_vowel/(other_to_consonant+other_to_other+other_to_vowel)) + "\t\t" + df.format((double)other_to_consonant/(other_to_consonant+other_to_other+other_to_vowel)) +"\t\t" + df.format((double)other_to_other/(other_to_consonant+other_to_other+other_to_vowel)) + "\t|");
            System.out.println();
            System.out.println("Todennäköisyysvektori: ("+df.format((double)total_vowels/total_chars)+","+df.format((double)total_consonants/total_chars)+","+df.format((double)total_others/total_chars)+").");
        }

    }
}
