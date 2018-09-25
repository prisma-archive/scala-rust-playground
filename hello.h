#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct Box_Connection {} Box_Connection;

typedef struct Box_Counter {} Box_Counter;

typedef struct Box_PsqlConnection {} Box_PsqlConnection;

typedef struct {
  uint64_t count;
} Counter;

typedef struct {
  Box_Connection connection;
} PsqlConnection;

const char *formatHello(const char *str);

const char *hello(void);

void increment(Counter *arg);

Box_PsqlConnection newConnection(const char *url);

Box_Counter newCounterByReference(void);

Counter newCounterByValue(void);

void printHello(void);

const char *processJson(const char *string);

const char *readFromDb(const char *query);

const char *sqlQuery(const PsqlConnection *conn, const char *query, const char *params);
