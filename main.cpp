#include "SmartToken.h"
#include "Token.h"
#include <iostream>
#include <vector>
#include "Tokenizer.h"


int main() {

    /*
    // Teste die Token-Klasse
    Token token("Test-Lexem", 1, 42);

    // Gib die Werte aus
    std::cout << "Lexem: " << token.getLexem() << std::endl;
    std::cout << "Row: " << token.getRow() << std::endl;
    std::cout << "Col: " << token.getCol() << std::endl;

    return 0;*/

    /*std::string input = "Dies ist ein Test der Tokenize-Funktion";
    std::vector<Token> tokens;

    tokenize(input, tokens);

    // Ausgabe der Tokens
    for (const Token& token : tokens) {
        std::cout << "Lexem: " << token.getLexem() << ", Zeile: " << token.getRow() << ", Spalte: " << token.getCol() << std::endl;
    }

    return 0;*/

    // Erstelle ein Token
    Token* token1 = new Token("Test-Lexem", 1, 1);

    // Erstelle einen SmartPointer
    SmartToken smartToken1(token1);
    std::cout << "Token 1: " << smartToken1.get()->getLexem()
        << " | RefCount: " << smartToken1.getCount() << std::endl;

    // Kopiere den SmartPointer
    SmartToken smartToken2 = smartToken1;
    std::cout << "Token 2: " << smartToken2.get()->getLexem()
        << " | RefCount: " << smartToken2.getCount() << std::endl;

   

    // Zuweisung des Smart Pointers
    SmartToken smartToken3;
    smartToken3 = smartToken2;
    std::cout << "Token 3: " << smartToken3.get()->getLexem()
        << " | RefCount: " << smartToken3.getCount() << std::endl;

    std::cout << "RefCount nach Zuweisung von smartToken3: " << smartToken3.getCount() << std::endl;

    // Entfernen eines Smart Pointers und RefCount beobachten
    std::cout << "Entfernen von smartToken2..." << std::endl;
    smartToken2 = SmartToken();  // Setze smartToken2 auf einen neuen SmartPointer, der den Zähler dekrementiert

    // Ausgabe des RefCounts nach dem Entfernen von smartToken2
    std::cout << "Token 1: " << smartToken1.get()->getLexem()
        << " | RefCount nach Entfernen von smartToken2: " << smartToken1.getCount() << std::endl;
    std::cout << "Token 3: " << smartToken3.get()->getLexem()
        << " | RefCount: " << smartToken3.getCount() << std::endl;


    // Entfernen von smartToken3 und RefCount beobachten
    std::cout << "Entfernen von smartToken3..." << std::endl;
    smartToken3 = SmartToken();  // Setze smartToken3 auf einen neuen SmartPointer

    // Ausgabe des RefCounts nach dem Entfernen von smartToken3
    std::cout << "Token 1: " << smartToken1.get()->getLexem()
        << " | RefCount nach Entfernen von smartToken3: " << smartToken1.getCount() << std::endl;


    return 0;
}
