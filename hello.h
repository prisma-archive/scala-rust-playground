#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct Box_Counter Box_Counter;

typedef struct PsqlConnection PsqlConnection;

typedef struct Box_PsqlConnection Box_PsqlConnection;

typedef struct {
  uint64_t count;
} Counter;

void startTransaction(PsqlConnection *conn);
void closeConnection(PsqlConnection *conn);
void commitTransaction(PsqlConnection *conn);
void rollbackTransaction(PsqlConnection *conn);

const char *formatHello(const char *str);
const char *hello(void);

void increment(Counter *arg);

Box_Counter newCounterByReference(void);
Box_PsqlConnection newConnection(const char *url);

Counter newCounterByValue(void);

void printHello(void);

const char *processJson(const char *string);

const char *readFromDb(const char *query);

const char *sqlQuery(const PsqlConnection *conn, const char *query, const char *params);
void sqlExecute(const PsqlConnection *conn, const char *query, const char *params);
