import { Component } from '@angular/core';

@Component({
  template:''
})
export class CardComponent<DataType> {
  visible = false;
  data: DataType | undefined;

  show() {
    this.visible = true;
  }

  hide() {
    this.visible = false;
  }

}
 