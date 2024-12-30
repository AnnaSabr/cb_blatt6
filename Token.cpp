#include "Token.h"
#include <cstring>

// Konstruktor
Token::Token(const char* l, int r, int c) : row(r), col(c) {
    size_t len = strlen(l) + 1;
    lexem = new char[len];
    strcpy_s(lexem, len, l);
}

// Destruktor
Token::~Token() {
    delete[] lexem;
}

// Copy-Konstruktor
Token::Token(const Token& other) : row(other.row), col(other.col) {
    size_t len = strlen(other.lexem) + 1;
    lexem = new char[len];
    strcpy_s(lexem, len, other.lexem);
}

// Copy-Zuweisungsoperator
Token& Token::operator=(const Token& other) {
    if (this != &other) {
        delete[] lexem; // Alten Speicher freigeben
        size_t len = strlen(other.lexem) + 1;
        lexem = new char[len];
        strcpy_s(lexem, len, other.lexem);
        row = other.row;
        col = other.col;
    }
    return *this;
}

// Getter
const char* Token::getLexem() const {
    return lexem;
}

int Token::getRow() const {
    return row;
}

int Token::getCol() const {
    return col;
}
