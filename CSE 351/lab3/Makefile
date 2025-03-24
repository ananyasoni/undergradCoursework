
UWNETID = `cat UW_ID.txt`

EXPLOITS = smoke.txt fizz.txt bang.txt dynamite.txt

test:
	@echo "performing tests for $(UWNETID)"
	@echo "run -g -u $(UWNETID) < __test.bytes >> __results.log" > __gdb.in
	@echo "quit" >> __gdb.in
	@touch __results.log
	@for e in $(EXPLOITS); do echo $$e >> __results.log; test -f $$e && ( ./sendstring < $$e > __test.bytes; gdb -x __gdb.in bufbomb; ) || echo "File not found - skipping" >> __results.log; echo >> __results.log; done
	@echo
	@echo == Summary ================================
	@cat __results.log
	@rm -f __gdb.in __test.bytes __results.log

clean:
	rm *.bytes
