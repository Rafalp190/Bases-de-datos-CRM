package relational
import scalikejdbc._

val takeid = 1
val newUserId = ?
val newName = "Alice"
val newLastname = "hola"
val newCompanyid = 1
val newUsertypeid = 1
val newGenderir = 1
val newAddresid = 1
val newNationalityid = 1
val newEthnicid = 1
val newExtrafieldid = 1
val newTwitter = "aaoliva"
val newEmail = "aaoliva@mail"
val newUsername = "aaoliva"
val newPassword = 1234
val newDatecreate = "2017-1-1"
val newLastlogin = "2017-1-1"
//val path = "\src\main\resources\static\pictures\"
val newPhoto = "hola.jpg"
val newEdad = "1990-1-1"
val newActivo = true

//updates cliente
	sql"Update users SET firstname = ${newName}, lastname = ${NewLastName}, 
	companyid = ${newCompanyID}, usertypeid = ${newUserTypeID}, salary = ${newSalary}, 
	addresid = ${newAddresID}, ethnicid = ${newEthnicID}, dateofbirth= new ${dateOfBirth}, 
	gender = ${newGender}, twitter = ${newTwitter}, email = ${newEmail}, username = ${newUserName}, 
	password = ${newPassword}, lastlogin = ${newLastLogin}, active = ${newActive}, photo = ${newPhoto}
	Where userid = ${newUserId}".map(rs => rs.string("firstname")).update.apply()

//insert un nuevo cliente
	sql"Insert in to users values(firstname = ${newName}, lastname = ${NewLastName}, 
	companyid = ${newCompanyID}, usertypeid = ${newUserTypeID}, salary = ${newSalary}, 
	addresid = ${newAddresID}, ethnicid = ${newEthnicID}, dateofbirth= new ${dateOfBirth}, 
	gender = ${newGender}, twitter = ${newTwitter}, email = ${newEmail}, username = ${newUserName}, 
	password = ${newPassword}, lastlogin = ${newLastLogin}, active = ${newActive}, photo = ${newPhoto}
	)".map(rs => rs.string("firstname")).update.apply()

//inert para la tabla de extrafielduser
	sql"insert into extrafielduser (userid, extrafieldid)
	values (thisUserid, thisExtraFieldid)".map(rs => rs.string("firstname")).update.apply()

//insert para la tabla de nationality user
	sql"insert into natuser (userid, nationalityid)
	values (thisUserid, ThisNationalityid)".map(rs => rs.string("firstname")).update.apply()

//

//Conteo de clientes por etnia
	sql"Select count (ethnicid)
	from users
	group by 
	ethnicid".map(rs=> re.string("")).update.apply()

//Conteo de clientes por nacionalidad
	sql"Select count (nationalityid)
	from users
	group by 
	nationalityid".map(rs=> re.string("")).update.apply()

//conteo de clientes por compañia
	sql"Select count (companyid)
	from users
	group by 
	companyid".map(rs=> re.string("")).update.apply()

//graficas clientes por genero

	sql"Select count (genderid)
	from users
	group by 
	genderid".map(rs=> re.string("")).update.apply()

//grafica Clientes por nacionalidad
	sql"Select count (companyid)
	from users
	group by 
	companyid".map(rs=> re.string("")).update.apply()

//grafica Clientes activos 
	sql"Select count (active)
	from users
	group by 
	active".map(rs=> re.string("")).update.apply()



// implicit session represents java.sql.Connection
val memberId: Option[Long] = DB readOnly { implicit session =>
  sql"select id from members where name = ${name}" // don't worry, prevents SQL injection
    .map(rs => rs.long("id")) // extracts values from rich java.sql.ResultSet
    .single                   // single, list, traversable
    .apply()                  // Side effect!!! runs the SQL using Connection
}
