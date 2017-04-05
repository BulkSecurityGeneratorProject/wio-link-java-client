import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { EventHandler, Language } from './event-handler.model';
import { EventHandlerPopupService } from './event-handler-popup.service';
import { EventHandlerService } from './event-handler.service';
@Component({
    selector: 'jhi-event-handler-dialog',
    templateUrl: './event-handler-dialog.component.html'
})
export class EventHandlerDialogComponent implements OnInit {

    eventHandler: EventHandler;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private eventHandlerService: EventHandlerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['eventHandler', 'language']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, eventHandler, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                eventHandler[field] = base64Data;
                eventHandler[`${field}ContentType`] = $file.type;
            });
        }
    }

    languageChanged() {
        switch(this.eventHandler.language.toString()) {
            case 'JAVA': {
                this.eventHandler.code = "";
                break;
            }
            case 'JAVASCRIPT': {
                this.eventHandler.code = "";
                break;
            }
            case 'GROOVYSCRIPT': {
                this.eventHandler.code = `package groovy.scripts



import java.lang.*
import java.util.*
import java.text.*
import java.time.*
import nl.openweb.iot.wio.domain.*
import nl.openweb.iot.wio.domain.grove.*
import nl.openweb.iot.wio.scheduling.*
import nl.openweb.iot.dashboard.service.script.AbstractGroovyEventHandler
import nl.openweb.iot.wio.WioException


class EventHandler extends AbstractGroovyEventHandler {

    @Override
    void handle(Map<String, String> event, Node node, TaskContext context) throws WioException {
        // System.out.println(event)
    }
}`;
                break;
            }

        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.eventHandler.id !== undefined) {
            this.eventHandlerService.update(this.eventHandler)
                .subscribe((res: EventHandler) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.eventHandlerService.create(this.eventHandler)
                .subscribe((res: EventHandler) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: EventHandler) {
        this.eventManager.broadcast({ name: 'eventHandlerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-event-handler-popup',
    template: ''
})
export class EventHandlerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private eventHandlerPopupService: EventHandlerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.eventHandlerPopupService
                    .open(EventHandlerDialogComponent, params['id']);
            } else {
                this.modalRef = this.eventHandlerPopupService
                    .open(EventHandlerDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
