#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct Box_Counter Box_Counter;

typedef struct {
  uint64_t count;
} Counter;

const char *formatHello(const char *str);

const char *hello(void);

void increment(Counter *arg);

Box_Counter newCounterByReference(void);

Counter newCounterByValue(void);

void printHello(void);

const char *processJson(const char *string);

const char *readFromDb(const char *query);
