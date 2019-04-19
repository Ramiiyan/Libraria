import { PipeTransform, Pipe } from '@angular/core';

@Pipe({
    name: 'bookFilter'
})

export class ItemFilterPipe implements PipeTransform {
    transform(items: any [], searchTerm: string): any {
        if (!items || !searchTerm) {
            return items;
        }

        return items.filter(item =>
            item.title.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1);
    }
}
