// RefCounter.h
#pragma once

class RefCounter {
public:
    // Standardkonstruktor
    RefCounter() : n(1) {}

    // Zähler erhöhen
    void inc() { n++; }

    // Zähler verringern
    void dec() {
        if (n > 0) n--;
    }

    // Prüfen, ob der Zähler auf 0 steht
    bool isZero() const { return n == 0; }

    unsigned int getCount() const { return n; }

private:
    unsigned int n; // Referenzzähler
};
