import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Product, Comment, ProductService} from "../shared/product.service";

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  private product: Product;
  private comments: Comment[];

  constructor(private routeInfo: ActivatedRoute,
  private productService: ProductService) { }

  ngOnInit() {
    const prodId: number = this.routeInfo.snapshot.params['prodId'];
    this.product = this.productService.getProduct(prodId);
    this.comments = this.productService.getCommentForProductId(prodId);
  }

}
