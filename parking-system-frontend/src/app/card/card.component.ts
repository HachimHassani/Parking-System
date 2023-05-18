import { Component } from '@angular/core';

@Component({
  template:''
})
export class CardComponent {
  visible = false;

  show() {
    this.visible = true;
  }

  hide() {
    this.visible = false;
  }

}
 