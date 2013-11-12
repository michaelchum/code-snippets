OBJECTS = login.o linked.o cipher.o 

loginTest : $(OBJECTS)
	gcc -o loginTest $(OBJECTS)

login.o : login.c
	gcc -c login.c

cipher.o : cipher.c
	gcc -c cipher.c

linked.o : linked.c
	gcc -c linked.c

backup:
	cp * ../backup

clean:
	rm -f *.o 