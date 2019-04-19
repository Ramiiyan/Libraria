import {AbstractControl, ValidatorFn} from '@angular/forms';

export function patternValidator(reg: RegExp): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any} | null => {
      const forbidden = reg.test(control.value);
      return forbidden ? { 'pattern': {value: control.value}} : null;
    };
}
