import { Component, OnInit } from '@angular/core';
import {isNumber} from 'util';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  private imgurl: string = 'http://placehold.it/320x150';
  private products: Array<Product>;

  constructor() { }

  ngOnInit() {
    this.products = [
      new Product(1, '第1个商品名称', 1.99, 3.5, '这是第一个商品', ['电子商品', '电子产品']),
      new Product(2, '第2个商品名称', 2.99, 4.5, '这是第二个商品', ['电子商品', '电子产品']),
      new Product(3, '第3个商品名称', 2.99, 1.5, '这是第三个商品', ['电子商品', '电子产品']),
      new Product(4, '第4个商品名称', 12.99, 0.5, '这是第四个商品', ['电子商品', '电子产品']),
      new Product(5, '第5个商品名称', 22.99, 5, '这是第五个商品', ['电子商品', '电子产品']),
      new Product(6, '第6个商品名称', 50, 3.5, '这是第六个商品', ['电子商品', '电子产品'])
    ];
  }

}

export class Product {
  constructor (
    public id: number,
    public title: string,
    public price: number,
    public rating: number,
    public desc: string,
    public categories: Array<string>
  ) {
  }
}
