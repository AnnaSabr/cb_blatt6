// SmartToken.cpp
#include "SmartToken.h"

// Konstruktor, der einen rohen Zeiger auf ein Token übernimmt
SmartToken::SmartToken(Token* p) : pObj(p), rc(nullptr) {
    if (pObj) {
        rc = new RefCounter(); // Neuen RefCounter für das Token anlegen
    }
}

// Copy-Konstruktor
SmartToken::SmartToken(const SmartToken& sp) : pObj(sp.pObj), rc(sp.rc) {
    if (rc) {
        rc->inc(); // Referenzzähler erhöhen
    }
}

// Destruktor
SmartToken::~SmartToken() {
    if (rc) {
        rc->dec(); // Referenzzähler verringern
        if (rc->isZero()) { // Wenn der Zähler 0 erreicht, löschen wir das Token
            delete pObj;    // Freigeben des Tokens
            delete rc;      // Freigeben des RefCounters
        }
    }
}

// Zuweisungsoperator
SmartToken& SmartToken::operator=(const SmartToken& sp) {
    if (this != &sp) { // Verhindern von Selbstzuweisung
        if (rc) {
            rc->dec(); // Den alten Zähler verringern
            if (rc->isZero()) { // Wenn der alte Zähler 0 erreicht, löschen wir das Token
                delete pObj;
                delete rc;
            }
        }

        pObj = sp.pObj;
        rc = sp.rc;

        if (rc) {
            rc->inc(); // Den Zähler des neuen Smart Tokens erhöhen
        }
    }
    return *this;
}
