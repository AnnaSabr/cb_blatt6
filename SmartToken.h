// SmartToken.h
#pragma once
#include "Token.h"
#include "RefCounter.h"

class SmartToken {
public:
    // Konstruktor, nimmt einen rohen Zeiger
    SmartToken(Token* p = nullptr);

    // Copy-Konstruktor
    SmartToken(const SmartToken& sp);

    // Destruktor
    ~SmartToken();

    // Zuweisungsoperator
    SmartToken& operator=(const SmartToken& sp);

    // Getter für das Token-Objekt
    Token* get() const { return pObj; }

    unsigned int getCount() const { return rc ? rc->getCount() : 0; }

private:
    Token* pObj;        // Zeiger auf das aktuelle geteilte Token
    RefCounter* rc;     // Zeiger auf den Referenzzähler
};
