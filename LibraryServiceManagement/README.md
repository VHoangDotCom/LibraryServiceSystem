1. Dependencies:
- Lombok
- Spring Web
- Spring Data JPA
- MySQL Driver
- Java mail sender
- Spring Security
- Spring Boot DevTools
- cloudinary-http44
- cloudinary-taglib
- commons-fileupload
- commons-io

2. Config application.properties
   spring.servlet.multipart.enabled=true

spring.datasource.url= jdbc:mysql://localhost:3306/file_db?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username= root
spring.datasource.password=

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
// create, create-drop
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.format_sql=true

// Threshold after which files are written to disk.
spring.servlet.multipart.file-size-threshold=2KB
// Max file size.
spring.servlet.multipart.max-file-size=200MB
#Max request size
spring.servlet.multipart.max-request-size=215MB

//Send Mail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=viethoang2001gun@gmail.com
spring.mail.password=gzghqjgukmjjkwaf
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

3. Tao cac package can thiet
   Controller, Config, Entity, Exception, Migration, Model, Repository, Security, Service

4. Tao cac template can thiet (mail,v.v..) trong folder /resources/templates

5. Tao 1 JUnitTest cua Repository
   chuot phai ten class -> Generate -> Test... ->

@SpringBootTest
@DataJdbcTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent() {
        Student student = Student.builder()
                .emailId("vh@gmail.com")
                .firstName("Shabbit")
                .lastName("Dawoodi")
                .guardianName("Nikhil")
                .guardianEmail("nikki@gmail.com")
                .guardianMobile("032434234")
                .build();
        
        studentRepository.save(student);
    }
}
==> recommend dung Test ( bo qua cac buoc tao ham trong Service va ServiceImpl
dong thoi ko phai set method trong controller )
