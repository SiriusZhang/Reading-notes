


CREATE TABLE `product` (
  `id` int(11) NOT NULL COMMENT 'id',
  `name` varchar(64) NOT NULL COMMENT '名字',
  `description` varchar(64) NOT NULL COMMENT '描述',
  `category` varchar(64) NOT NULL COMMENT '类型',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
  PRIMARY KEY (`id`),
  UNIQUE(`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保险类型信息';

INSERT INTO `product`(`id`, `name`, `description`, `category`, `price`, `create_time`) VALUES 
(1, 'Kayak', 'A boat for one person', 'Watersports', 275, CURRENT_TIMESTAMP),
(2, 'Lifejacket', 'Protective and fashionable', 'Watersports', 48.95, CURRENT_TIMESTAMP),
(3, 'Soccer Ball', 'FIFA-approved size and weigth', 'Soccer', 19.5, CURRENT_TIMESTAMP),
(4, 'Corner Flags', 'Give your playing field a professional touch', 'Soccer', 34.95, CURRENT_TIMESTAMP),
(5, 'Stadium', 'Flat-packed 35,000-seat stadium', 'Soccer', 79500.00, CURRENT_TIMESTAMP),
(6, 'Thinking Cap', 'Improve your brain efficiency by 75%', 'chess', 16, CURRENT_TIMESTAMP),
(7, 'Unsteady Chair', 'Secretly give your opponent a disadvantage', 'chess', 29.95, CURRENT_TIMESTAMP),
(8, 'Humam Chess Board', 'A fun game for the family', 'chess', 75, CURRENT_TIMESTAMP),
(9, 'Bliing-Bling King', 'Gold-plated, diamond-studded King', 'chess', 75, CURRENT_TIMESTAMP);