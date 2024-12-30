#include "Tokenizer.h"
#include <sstream>

// Die tokenize-Funktion, die den Eingabestring in Tokens aufteilt.
void tokenize(const std::string& input, std::vector<Token>& tokens) {
    std::istringstream stream(input);  // Input-Stream erstellen
    std::string word;
    size_t col = 1;  // Wir gehen davon aus, dass die Eingabe in einer einzigen Zeile ist
    while (stream >> word) {  // Solange es noch Wörter gibt, die gesplittet werden können
        Token token(word.c_str(), 1, col);  // Token erstellen (wir setzen die Zeile auf 1 und Spalte auf die aktuelle Position)
        tokens.push_back(token);  // Token in den Vektor hinzufügen
        col += word.length() + 1;  // Nächste Spalte (wir fügen 1 für das Leerzeichen hinzu)
    }
}
