import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICalendarAttribute, defaultValue } from 'app/shared/model/calendar-attribute.model';

export const ACTION_TYPES = {
  FETCH_CALENDARATTRIBUTE_LIST: 'calendarAttribute/FETCH_CALENDARATTRIBUTE_LIST',
  FETCH_CALENDARATTRIBUTE: 'calendarAttribute/FETCH_CALENDARATTRIBUTE',
  CREATE_CALENDARATTRIBUTE: 'calendarAttribute/CREATE_CALENDARATTRIBUTE',
  UPDATE_CALENDARATTRIBUTE: 'calendarAttribute/UPDATE_CALENDARATTRIBUTE',
  DELETE_CALENDARATTRIBUTE: 'calendarAttribute/DELETE_CALENDARATTRIBUTE',
  RESET: 'calendarAttribute/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICalendarAttribute>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CalendarAttributeState = Readonly<typeof initialState>;

// Reducer

export default (state: CalendarAttributeState = initialState, action): CalendarAttributeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CALENDARATTRIBUTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CALENDARATTRIBUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CALENDARATTRIBUTE):
    case REQUEST(ACTION_TYPES.UPDATE_CALENDARATTRIBUTE):
    case REQUEST(ACTION_TYPES.DELETE_CALENDARATTRIBUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CALENDARATTRIBUTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CALENDARATTRIBUTE):
    case FAILURE(ACTION_TYPES.CREATE_CALENDARATTRIBUTE):
    case FAILURE(ACTION_TYPES.UPDATE_CALENDARATTRIBUTE):
    case FAILURE(ACTION_TYPES.DELETE_CALENDARATTRIBUTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CALENDARATTRIBUTE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CALENDARATTRIBUTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CALENDARATTRIBUTE):
    case SUCCESS(ACTION_TYPES.UPDATE_CALENDARATTRIBUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CALENDARATTRIBUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/calendar-attributes';

// Actions

export const getEntities: ICrudGetAllAction<ICalendarAttribute> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CALENDARATTRIBUTE_LIST,
    payload: axios.get<ICalendarAttribute>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICalendarAttribute> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CALENDARATTRIBUTE,
    payload: axios.get<ICalendarAttribute>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICalendarAttribute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CALENDARATTRIBUTE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICalendarAttribute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CALENDARATTRIBUTE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICalendarAttribute> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CALENDARATTRIBUTE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
