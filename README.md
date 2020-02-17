# petstore-test
 An executable test framework for testing REST app
 
 Could be executed from command-line with mandatory -tags parameter:
 java -jar <filename>.jar -tags=Test1,Test2 --url=http://someurl.local/petstore/
 
 ##com.example.pet.test
  contains test cases 
 
 ##com.example.pet.entities
  contains model records for JSON serialization 
  DeletetableByIdRecord: an abstraction for the models which could be deleted by the record id. To be used by the clean-up methods to remove the records which were created by the tests.  
 
 ##com.example.pet.core
  PetTestExecutor: executes the test cases with the given tags, against given URL.
  PetstoreCRUDHelper: performs common actions for test cases. Manages clean-up
  TestTag: class-level tag, used by the executor to run the test 
  ServiceLocator: provides services to tests, helps to decouple the implementation from usage
  _Factory: services implementations, should be used in tests via service locator
  
  
