#include <stdlib.h>
#include <stdint.h>

typedef struct ProtoBuf {
    uint8_t *data;
    uintptr_t len;
} ProtoBuf;

ProtoBuf *pb_output();
void *pb_input(const char *data, uintptr_t len);
void *destroy(ProtoBuf *data);
