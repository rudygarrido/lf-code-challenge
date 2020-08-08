import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {GreetingService} from '../services/greeting.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-form-material',
  templateUrl: './form-material.component.html',
  styleUrls: ['./form-material.component.scss']
})
export class FormMaterialComponent implements OnInit {

  form: FormGroup;

  id: string;

  message: string;

  greetingSaved = false;

  constructor(private greetingService: GreetingService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      id : new FormControl(''),
      message : new FormControl('')
    });
    this.greetingService.getDefault().subscribe(
      data => {
        this.id = data.id;
        this.message = data.message;
      },
      error => {
        this.showSnackBar('Error obtaining greeting.');
      }
    );
  }
  getGreeting(): void {
    this.greetingService.get(this.form.get('id').value)
      .subscribe(
        data => {
          this.id = data.id;
          this.message = data.message;
          this.clearForm();
          this.showSnackBar('Greeting Obtained! Now displaying.');
        },
        error => {
          this.showSnackBar('Error obtaining greeting. Please check id.');
        }
      );
  }

  getDefaultGreeting(): void {
    this.greetingService.getDefault()
      .subscribe(
        data => {
          this.id = data.id;
          this.message = data.message;
          this.showSnackBar('Default greeting Obtained! Now displaying.');
        },
        error => {
          this.showSnackBar('Error obtaining greeting. Please check id.');
        }
      );
  }

  saveGreeting(): void {
    const greeting = {
      id : this.form.get('id').value,
      message : this.form.get('message').value
    };
    this.greetingService.create(greeting).subscribe(
      data => {
        this.id = data.id;
        this.message = data.message;
        this.clearForm();
        this.showSnackBar('Greeting Saved! Now displaying.');
      },
      error => {
        this.showSnackBar('Error saving greeting.');
      }
    );
  }

  updateGreeting(): void {
    const greeting = {
      id : this.form.get('id').value,
      message : this.form.get('message').value
    };
    this.greetingService.update(this.form.get('id').value, greeting).subscribe(
      data => {
        this.id = data.id;
        this.message = data.message;
        this.clearForm();
        this.showSnackBar('Greeting Updated! Now displaying.');
      },
      error => {
        this.showSnackBar('Error updating greeting.');
      }
    );
  }

  clearForm(): void {
    this.form.get('id').setValue('');
    this.form.get('message').setValue('');
  }

  loadValuesOnForm(): void {
    this.form.get('id').setValue(this.id);
    this.form.get('message').setValue(this.message);
  }

  showSnackBar(message): void {
      this.snackBar.open(message, null, {
        duration: 2000,
      });
  }

}
