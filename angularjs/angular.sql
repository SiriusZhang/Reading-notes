


CREATE TABLE IF NOT EXISTS `product` (
    `name` varchar(64) NOT NULL COMMENT '名字',
    `description` varchar(64) NOT NULL COMMENT '描述',
    `category` varchar(64) NOT NULL COMMENT '类型',
    `price` decimal(10,2) NOT NULL COMMENT '价格',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间', 
    PRIMARY KEY (`name`) ,
    UNIQUE (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保险类型信息';

INSERT INTO `product`(`name`, `description`, `category`, `price`, `create_time`) VALUES 
('Kayak', 'A boat for one person', 'Watersports', 275, CURRENT_TIMESTAMP),
('Lifejacket', 'Protective and fashionable', 'Watersports', 48.95, CURRENT_TIMESTAMP),
('Soccer Ball', 'FIFA-approved size and weigth', 'Soccer', 19.5, CURRENT_TIMESTAMP),
('Corner Flags', 'Give your playing field a professional touch', 'Soccer', 34.95, CURRENT_TIMESTAMP),
('Stadium', 'Flat-packed 35,000-seat stadium', 'Soccer', 79500.00, CURRENT_TIMESTAMP),
('Thinking Cap', 'Improve your brain efficiency by 75%', 'chess', 16, CURRENT_TIMESTAMP),
('Unsteady Chair', 'Secretly give your opponent a disadvantage', 'chess', 29.95, CURRENT_TIMESTAMP),
('Humam Chess Board', 'A fun game for the family', 'chess', 75, CURRENT_TIMESTAMP),
('Bliing-Bling King', 'Gold-plated, diamond-studded King', 'chess', 75, CURRENT_TIMESTAMP);