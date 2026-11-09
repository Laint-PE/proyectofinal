CREATE DATABASE IF NOT EXISTS veterinaria_huellitas_final;
USE veterinaria_huellitas_final;

CREATE TABLE IF NOT EXISTS sucursal (
                                        id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(30),
    horario_atencion VARCHAR(120),
    activo BOOLEAN NOT NULL DEFAULT TRUE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS persona (
                                       dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT NOT NULL DEFAULT 0,
    telefono VARCHAR(30)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cliente (
                                       id_cliente INT AUTO_INCREMENT PRIMARY KEY,
                                       dni VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(150),
    direccion VARCHAR(200),
    CONSTRAINT fk_cliente_persona FOREIGN KEY (dni) REFERENCES persona(dni)
    ON UPDATE CASCADE ON DELETE CASCADE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS empleado (
                                        id_empleado INT AUTO_INCREMENT PRIMARY KEY,
                                        dni VARCHAR(20) NOT NULL UNIQUE,
    cargo VARCHAR(80) NOT NULL,
    id_sucursal INT NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'ACTIVO',
    CONSTRAINT fk_empleado_persona FOREIGN KEY (dni) REFERENCES persona(dni)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_empleado_sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)
    ON UPDATE CASCADE ON DELETE RESTRICT
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS usuario (
                                       id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                                       usuario VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(40) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    id_empleado INT NOT NULL UNIQUE,
    CONSTRAINT fk_usuario_empleado FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
    ON UPDATE CASCADE ON DELETE CASCADE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS animal (
                                      codigo VARCHAR(30) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    raza VARCHAR(100),
    sexo VARCHAR(20),
    edad INT NOT NULL DEFAULT 0,
    peso DECIMAL(8,2) NOT NULL DEFAULT 0,
    id_cliente INT NOT NULL,
    CONSTRAINT fk_animal_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
    ON UPDATE CASCADE ON DELETE RESTRICT
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS perro (
                                     codigo VARCHAR(30) PRIMARY KEY,
    nivel_entrenamiento VARCHAR(80),
    requiere_bozal BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_perro_animal FOREIGN KEY (codigo) REFERENCES animal(codigo)
    ON UPDATE CASCADE ON DELETE CASCADE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS gato (
                                    codigo VARCHAR(30) PRIMARY KEY,
    temperamento VARCHAR(100),
    requiere_transportadora BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_gato_animal FOREIGN KEY (codigo) REFERENCES animal(codigo)
    ON UPDATE CASCADE ON DELETE CASCADE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS consulta (
                                        id_consulta INT AUTO_INCREMENT PRIMARY KEY,
                                        codigo_animal VARCHAR(30) NOT NULL,
    id_veterinario INT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    motivo VARCHAR(250) NOT NULL,
    sintomas TEXT,
    diagnostico TEXT,
    tratamiento TEXT,
    costo DECIMAL(10,2) NOT NULL DEFAULT 0,
    estado VARCHAR(30) NOT NULL DEFAULT 'REGISTRADA',
    CONSTRAINT fk_consulta_animal FOREIGN KEY (codigo_animal) REFERENCES animal(codigo)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_consulta_veterinario FOREIGN KEY (id_veterinario) REFERENCES empleado(id_empleado)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    INDEX idx_consulta_fecha (fecha)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS categoria (
                                         id_categoria INT AUTO_INCREMENT PRIMARY KEY,
                                         nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS producto (
                                        codigo VARCHAR(30) PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    id_categoria INT NOT NULL,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_producto_precio CHECK (precio >= 0),
    CONSTRAINT chk_producto_stock CHECK (stock >= 0)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS accesorio (
                                         codigo VARCHAR(30) PRIMARY KEY,
    tipo VARCHAR(80), material VARCHAR(80), ropa VARCHAR(80),
    CONSTRAINT fk_accesorio_producto FOREIGN KEY (codigo) REFERENCES producto(codigo)
    ON UPDATE CASCADE ON DELETE CASCADE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS alimento (
                                        codigo VARCHAR(30) PRIMARY KEY,
    tipo_animal VARCHAR(60), fecha_vencimiento DATE, sabor VARCHAR(80),
    CONSTRAINT fk_alimento_producto FOREIGN KEY (codigo) REFERENCES producto(codigo)
    ON UPDATE CASCADE ON DELETE CASCADE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS medicamento (
                                           codigo VARCHAR(30) PRIMARY KEY,
    fecha_vencimiento DATE, dosis VARCHAR(120),
    CONSTRAINT fk_medicamento_producto FOREIGN KEY (codigo) REFERENCES producto(codigo)
    ON UPDATE CASCADE ON DELETE CASCADE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS promocion (
                                         id_promocion INT AUTO_INCREMENT PRIMARY KEY,
                                         nombre VARCHAR(120) NOT NULL,
    descuento DECIMAL(7,2) NOT NULL,
    inicio_descuento DATE NOT NULL,
    fin_descuento DATE NOT NULL,
    cantidad_minima INT NOT NULL DEFAULT 1,
    CONSTRAINT chk_promocion_descuento CHECK (descuento >= 0),
    CONSTRAINT chk_promocion_cantidad CHECK (cantidad_minima > 0)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pedido (
                                      id_pedido INT AUTO_INCREMENT PRIMARY KEY,
                                      id_cliente INT NOT NULL,
                                      id_empleado INT NOT NULL,
                                      fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    tipo_entrega VARCHAR(40) NOT NULL,
    direccion_entrega VARCHAR(200),
    id_repartidor INT NULL,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_pedido_empleado FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_pedido_repartidor FOREIGN KEY (id_repartidor) REFERENCES empleado(id_empleado)
    ON UPDATE CASCADE ON DELETE SET NULL,
    INDEX idx_pedido_fecha (fecha)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS detalle_pedido (
                                              id_detalle INT AUTO_INCREMENT PRIMARY KEY,
                                              id_pedido INT NOT NULL,
                                              codigo_producto VARCHAR(30) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) NOT NULL DEFAULT 0,
    subtotal DECIMAL(10,2) NOT NULL,
    id_promocion INT NULL,
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (codigo_producto) REFERENCES producto(codigo)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_detalle_promocion FOREIGN KEY (id_promocion) REFERENCES promocion(id_promocion)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_detalle_cantidad CHECK (cantidad > 0)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pago (
                                    id_pago INT AUTO_INCREMENT PRIMARY KEY,
                                    id_pedido INT NOT NULL,
                                    metodo_pago VARCHAR(50) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado_pago VARCHAR(30) NOT NULL DEFAULT 'PAGADO',
    monto_recibido DECIMAL(10,2) NOT NULL DEFAULT 0,
    vuelto DECIMAL(10,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_pago_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
    ON UPDATE CASCADE ON DELETE CASCADE,
    INDEX idx_pago_fecha (fecha_pago)
    ) ENGINE=InnoDB;

INSERT IGNORE INTO sucursal
  (id_sucursal, nombre, direccion, telefono, horario_atencion, activo)
VALUES
  (1, 'Sucursal Principal', 'Av. Principal 123', '999111222', 'Lun-Sáb 09:00-18:00', TRUE);

INSERT IGNORE INTO persona (dni, nombre, apellido, edad, telefono) VALUES
  ('00000001', 'Administrador', 'General', 30, '999000001'),
  ('00000002', 'Cliente', 'Ejemplo', 28, '999000002'),
  ('00000003', 'María', 'López', 35, '999000003');

INSERT IGNORE INTO empleado (id_empleado, dni, cargo, id_sucursal, estado) VALUES
  (1, '00000001', 'Veterinario', 1, 'ACTIVO');

INSERT IGNORE INTO usuario (id_usuario, usuario, password, rol, activo, id_empleado) VALUES
  (1, 'USUARIO01', '123456', 'ADMIN', TRUE, 1);

INSERT IGNORE INTO cliente (id_cliente, dni, correo, direccion) VALUES
  (1, '00000002', 'cliente@example.com', 'Calle Los Olivos 456'),
  (2, '00000003', 'maria@example.com', 'Jr. Las Flores 789');

INSERT IGNORE INTO animal (codigo, nombre, raza, sexo, edad, peso, id_cliente) VALUES
  ('ANI-0001', 'Firulais', 'Mestizo', 'Macho', 3, 12.50, 1),
  ('ANI-0002', 'Michi', 'Doméstico', 'Hembra', 2, 4.20, 2);
INSERT IGNORE INTO perro (codigo, nivel_entrenamiento, requiere_bozal)
VALUES ('ANI001', 'Básico', FALSE);
INSERT IGNORE INTO gato (codigo, temperamento, requiere_transportadora)
VALUES ('ANI002', 'Tranquilo', TRUE);

INSERT IGNORE INTO categoria (id_categoria, nombre, descripcion) VALUES
  (1, 'Alimentos', 'Alimentos para mascotas'),
  (2, 'Accesorios', 'Accesorios de uso diario'),
  (3, 'Medicamentos', 'Medicamentos veterinarios');
INSERT IGNORE INTO producto (codigo, nombre, precio, stock, id_categoria) VALUES
  ('PRO-0001', 'Alimento balanceado adulto', 45.90, 20, 1),
  ('PRO-0002', 'Correa mediana', 25.00, 15, 2),
  ('PRO-0003', 'Antiparasitario genérico', 18.50, 10, 3);
INSERT IGNORE INTO alimento (codigo, tipo_animal, fecha_vencimiento, sabor)
VALUES ('ALI001', 'Perro', '2027-12-31', 'Pollo');
INSERT IGNORE INTO accesorio (codigo, tipo, material, ropa)
VALUES ('ACC001', 'Correa', 'Nylon', 'No aplica');
INSERT IGNORE INTO medicamento (codigo, fecha_vencimiento, dosis)
VALUES ('MED001', '2027-06-30', 'Según indicación veterinaria');

INSERT IGNORE INTO promocion
  (id_promocion, nombre, descuento, inicio_descuento, fin_descuento, cantidad_minima)
VALUES (1, 'Descuento de ejemplo', 5.00, '2026-01-01', '2027-12-31', 2);

INSERT IGNORE INTO consulta
  (id_consulta, codigo_animal, id_veterinario, fecha, motivo, sintomas,
   diagnostico, tratamiento, costo, estado)
VALUES
  (1, 'ANI001', 1, CURRENT_TIMESTAMP, 'Control general', 'Sin síntomas relevantes',
   'Buen estado general', 'Control rutinario', 30.00, 'REGISTRADA');

INSERT IGNORE INTO pedido
  (id_pedido, id_cliente, id_empleado, fecha, estado, tipo_entrega, direccion_entrega, id_repartidor)
VALUES
  (1, 1, 1, CURRENT_TIMESTAMP, 'PAGADO', 'RECOJO', NULL, NULL);
INSERT IGNORE INTO detalle_pedido
  (id_detalle, id_pedido, codigo_producto, cantidad, precio_unitario, descuento, subtotal, id_promocion)
VALUES (1, 1, 'ALI001', 1, 45.90, 0.00, 45.90, NULL);
INSERT IGNORE INTO pago
  (id_pago, id_pedido, metodo_pago, monto, fecha_pago, estado_pago, monto_recibido, vuelto)
VALUES (1, 1, 'EFECTIVO', 45.90, CURRENT_TIMESTAMP, 'PAGADO', 50.00, 4.10);

DROP PROCEDURE IF EXISTS sp_descontar_stock;
DROP PROCEDURE IF EXISTS sp_registrar_consulta;
DELIMITER $$
CREATE PROCEDURE sp_descontar_stock(
    IN p_codigo VARCHAR(30), IN p_cantidad INT,
    OUT p_exito TINYINT, OUT p_mensaje VARCHAR(255)
)
BEGIN
  DECLARE v_stock INT DEFAULT NULL;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_stock = NULL;
SELECT stock INTO v_stock FROM producto WHERE codigo = p_codigo FOR UPDATE;
IF v_stock IS NULL THEN
    SET p_exito = 0; SET p_mensaje = 'El producto no existe.';
  ELSEIF p_cantidad <= 0 THEN
    SET p_exito = 0; SET p_mensaje = 'La cantidad debe ser mayor que cero.';
  ELSEIF v_stock < p_cantidad THEN
    SET p_exito = 0; SET p_mensaje = 'Stock insuficiente.';
ELSE
UPDATE producto SET stock = stock - p_cantidad WHERE codigo = p_codigo;
SET p_exito = 1; SET p_mensaje = 'Stock actualizado.';
END IF;
END$$

CREATE PROCEDURE sp_registrar_consulta(
    IN p_codigo_animal VARCHAR(30), IN p_id_veterinario INT,
    IN p_motivo VARCHAR(250), IN p_sintomas TEXT, IN p_diagnostico TEXT,
    IN p_tratamiento TEXT, IN p_costo DECIMAL(10,2),
    OUT p_exito TINYINT, OUT p_mensaje VARCHAR(255), OUT p_id_consulta INT
)
BEGIN
INSERT INTO consulta (codigo_animal, id_veterinario, motivo, sintomas,
                      diagnostico, tratamiento, costo)
VALUES (p_codigo_animal, p_id_veterinario, p_motivo, p_sintomas,
        p_diagnostico, p_tratamiento, p_costo);
SET p_id_consulta = LAST_INSERT_ID();
  SET p_exito = 1;
  SET p_mensaje = 'Consulta registrada correctamente.';
END$$
DELIMITER ;
