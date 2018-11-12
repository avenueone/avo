import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVessel, defaultValue } from 'app/shared/model/vessel.model';

export const ACTION_TYPES = {
  FETCH_VESSEL_LIST: 'vessel/FETCH_VESSEL_LIST',
  FETCH_VESSEL: 'vessel/FETCH_VESSEL',
  CREATE_VESSEL: 'vessel/CREATE_VESSEL',
  UPDATE_VESSEL: 'vessel/UPDATE_VESSEL',
  DELETE_VESSEL: 'vessel/DELETE_VESSEL',
  RESET: 'vessel/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVessel>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type VesselState = Readonly<typeof initialState>;

// Reducer

export default (state: VesselState = initialState, action): VesselState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VESSEL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VESSEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VESSEL):
    case REQUEST(ACTION_TYPES.UPDATE_VESSEL):
    case REQUEST(ACTION_TYPES.DELETE_VESSEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VESSEL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VESSEL):
    case FAILURE(ACTION_TYPES.CREATE_VESSEL):
    case FAILURE(ACTION_TYPES.UPDATE_VESSEL):
    case FAILURE(ACTION_TYPES.DELETE_VESSEL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VESSEL_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VESSEL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VESSEL):
    case SUCCESS(ACTION_TYPES.UPDATE_VESSEL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VESSEL):
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

const apiUrl = 'api/vessels';

// Actions

export const getEntities: ICrudGetAllAction<IVessel> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VESSEL_LIST,
    payload: axios.get<IVessel>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IVessel> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VESSEL,
    payload: axios.get<IVessel>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVessel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VESSEL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVessel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VESSEL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVessel> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VESSEL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
