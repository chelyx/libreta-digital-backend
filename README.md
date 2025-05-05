# Libreta Digital Backend

Este es el backend del proyecto **Libreta Digital**, desarrollado en Java usando Spring Boot, Hibernate (JPA) y Maven.

---

## 📦 Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- Maven
- H2 (base de datos en memoria, para desarrollo)
- MySQL (para producción, opcional)

---

## 🚀 Cómo correr el proyecto

1️⃣ Clonar el repositorio:

```bash
git clone https://github.com/chelyx/libreta-digital-backend.git
cd libreta-digital-backend
```

2️⃣ Compilar:

```bash
mvn clean install
```

3️⃣ Correr:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en:

```
http://localhost:8080
```

---

## 🛢️ Configuración de base de datos

Por defecto usa H2 (en memoria) para desarrollo.  
Podés cambiar a MySQL editando el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/libretadigital
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```

---

## 📬 Endpoints disponibles

Ejemplo de endpoint en `AlumnoController`:

```
GET /alumnos
POST /alumnos
```

---

## ✨ Contribuciones

Si querés colaborar, abrí un issue o hacé un pull request.  
¡Toda ayuda es bienvenida!

---

## 📄 Licencia

MIT License
